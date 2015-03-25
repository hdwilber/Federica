package code
package lib
package request

import code.model.productive.ProductiveUnit
import code.model.event._
import net.liftweb.common.{Empty, Box}
import net.liftweb.http.RequestVar
import code.model.proposal.{Program, Area}

package object request {
  object productiveRequestVar extends RequestVar[Box[ProductiveUnit]](Empty)
  object productiveDeleteRequestVar extends RequestVar[List[ProductiveUnit]](Nil)
  object eventRequestVar extends RequestVar[Box[Event]](Empty)
  object eventDeleteRequestVar extends RequestVar[List[Event]](Nil)
  object areaRequestVar extends RequestVar[Box[Area]](Empty)
  object areaDeleteRequestVar extends RequestVar[List[Area]](Nil)
  object programRequestVar extends RequestVar[List[Program]](Nil)
  object programDeleteRequestVar extends RequestVar[List[Program]](Nil)
}
