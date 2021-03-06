package bootstrap.liftweb

import java.util.Locale
import javax.mail.internet.MimeMessage

import code.config._
import code.lib.ElasticSearch
import code.model.{SystemUser, User}
import net.liftmodules.FoBo
import net.liftmodules.extras.{Gravatar, LiftExtras}
import net.liftmodules.mongoauth.MongoAuth
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.http.provider.HTTPRequest
import net.liftweb.util._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    logger.info("Run Mode: "+Props.mode.toString)

    // init mongodb
    MongoConfig.init()
    FoBo.InitParam.ToolKit=FoBo.AngularJS141
    FoBo.InitParam.ToolKit=FoBo.AJSNGGrid207
    FoBo.init()

    // init auth-mongo
    MongoAuth.authUserMeta.default.set(User)
    MongoAuth.loginTokenAfterUrl.default.set(Site.password.url)
    MongoAuth.siteName.default.set("mARTadero")
    MongoAuth.systemEmail.default.set(SystemUser.user.email.get)
    MongoAuth.systemUsername.default.set(SystemUser.user.name.get)

    // For S.loggedIn_? and TestCond.loggedIn/Out builtin snippet
    LiftRules.loggedInTest = Full(() => User.isLoggedIn)

    // checks for ExtSession cookie
    LiftRules.earlyInStateful.append(User.testForExtSession)

    // Gravatar
    Gravatar.defaultImage.default.set("wavatar")

    // config an email sender
    SmtpMailer.init

    //Elasticsearch
    //ElasticSearch.updateAllIndeces

    // where to search snippet
    LiftRules.addToPackages("code")

    // set the default htmlProperties
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    // Build SiteMap
    LiftRules.setSiteMap(Site.siteMap)

    // Error handler
    ErrorHandler.init

    // 404 handler
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) =>
        NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })

    // Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-spinner").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-spinner").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Init Extras
    LiftExtras.init()

    // don't include the liftAjax.js code. It's served statically.
    LiftRules.autoIncludeAjaxCalc.default.set(() => () => (session: LiftSession) => false)

    LiftRules.localeCalculator = localeCalculator

    def localeCalculator(request: Box[HTTPRequest]): Locale = new Locale("es", "ES")

    net.liftmodules.ng.Angular.init(
      // Set to true if you plan to use futures. False otherwise to avoid an unneeded comet
      futures = true,

      // Set to the CSS selector for finding your apps in the page.
      appSelector = "[ng-app]",

      // Set to true to include a script tag with the src set to the path for liftproxy.js.
      // Set to false if you want to handle that yourself by referring to the path in
      // net_liftmodules_ng.
      includeJsScript = true,

      // Set to true to include angular.js from org.webjars angularjs
      includeAngularJs = true,

      // Add any additional js modules you want to load in the page from the angularjs webjar
      additionalAngularJsModules = List("animate", "cookies", "loader", "resource", "route", "sanitize", "scenario", "touch"),

      // Set to true to also deliver the angular-csp.css stylesheet on the page
      includeAngularCspCss = true,

      // Set to true to preserve the order of ajax service calls even in the event of server communication failures
      retryAjaxInOrder = true
    )

    LiftRules.dispatch.append(code.rest.AjaxFileUpload)

    // Mailer
    Mailer.devModeSend.default.set((m: MimeMessage) => logger.info("Dev mode message:\n" + prettyPrintMime(m)))
    Mailer.testModeSend.default.set((m: MimeMessage) => logger.info("Test mode message:\n" + prettyPrintMime(m)))
  }

  private def prettyPrintMime(m: MimeMessage): String = {
    val buf = new StringBuilder
    val hdrs = m.getAllHeaderLines
    while (hdrs.hasMoreElements)
      buf ++= hdrs.nextElement.toString + "\n"

    val out =
      """
        |%s
        |====================================
        |%s
      """.format(buf.toString, m.getContent.toString).stripMargin

    out
  }
}
