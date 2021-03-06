import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases",
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  object Ver {
    val lift = "2.6.2"
    val lift_edition = "2.6"
    val jetty = "9.2.2.v20140723"
    val angular_version = "1.4.7"
  }

  // Lift
  val liftWebkit = "net.liftweb" %% "lift-webkit" % Ver.lift
  val liftMongodb = "net.liftweb" %% "lift-mongodb-record" % Ver.lift

  // Lift modules
  val liftExtras = "net.liftmodules" %% ("extras_"+Ver.lift_edition) % "0.4-SNAPSHOT"
  val liftMongoauth = "net.liftmodules" %% ("mongoauth_"+Ver.lift_edition) % "0.6-SNAPSHOT"
  val liftFoBo = "net.liftmodules" %% "fobo_2.6"    % "1.4"

  // Rogue
  val rogueField = "com.foursquare" %% "rogue-field" % "2.5.0" intransitive()
  val rogueCore = "com.foursquare" %% "rogue-core" % "2.5.1" intransitive()
  val rogueLift = "com.foursquare" %% "rogue-lift" % "2.5.1" intransitive()
  val rogueIndex = "com.foursquare" %% "rogue-index" % "2.5.1" intransitive()

  // Jetty
  val jettyWebapp = "org.eclipse.jetty" % "jetty-webapp" % Ver.jetty
  val jettyPlus = "org.eclipse.jetty" % "jetty-plus" % Ver.jetty
  val servlet = "javax.servlet" % "javax.servlet-api" % "3.0.1"

  // Misc
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.2"
  val scalatest = "org.scalatest" %% "scalatest" % "2.2.1"
  val imaging = "net.liftmodules" % "imaging_2.6_2.10" % "1.3"
  val angularJs = "org.webjars"             %   "angularjs" % Ver.angular_version
  val liftNg = "net.liftmodules"   %% ("ng_"+Ver.lift_edition) % "0.9.0"

  //Twitter
  val finagleHttp = "com.twitter" % "finagle-http_2.11" % "6.24.0"

  //reCaptcha
  val reCaptcha = "net.tanesha.recaptcha4j" % "recaptcha4j" % "0.0.7"

}
