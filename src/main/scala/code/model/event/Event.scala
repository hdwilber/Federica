package code
package model
package event

import code.lib.RogueMetaRecord
import code.model.project._
import net.liftweb.util.Helpers._
import net.liftweb.mongodb.record.MongoRecord
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field.{TextareaField, LongField, DecimalField, StringField}
import code.model.proposal.{ActionLine, Area, Program}
import code.model.activity.{Activity, ActivityType}
import code.model.productive.ProductiveUnit
import net.liftweb.common.{Empty, Box}
import scala.xml.Elem
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds._
import code.model.event.EventType
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Any

class Event private() extends MongoRecord[Event] with ObjectIdPk[Event]{

  override def meta = Event

  object eventNumber extends LongField(this)
  object name extends StringField(this, 200)
  object schedule extends ObjectIdRefField(this, Schedule)
  object costInfo extends ObjectIdRefField(this, CostInfo)
  object eventTypes extends ObjectIdRefListField(this, EventType){
    def currentValue: List[EventType] = Nil
    def availableOptions = {
      val list = (EventType.createRecord.name("Taller") -> "Taller") ::
        (EventType.createRecord.name("Presentation") -> "Presentation") :: Nil
      list.toSeq
    }
    override def toForm: Box[Elem] = {
      tryo(SHtml.multiSelectObj(availableOptions,
        currentValue,
        (list : List[EventType]) => set(list.map(_.id.get)),
        "class" -> "select2 form-control",
        "data-placeholder" -> "Seleccione uno o varios tipos de evento.."
      ))
    }
  }

  object area extends ObjectIdRefField(this, Area){
    /*
    override def defaultValueBox = valueBox
    def availableOptions = Area.findAll.toList.map(s => s).toSeq
    override def toForm = {
      tryo(SHtml.ajaxSelectElem( availableOptions, defaultValueBox)(a => {set(a)})
      )
    }*/

      override def defaultValueBox: Box[MyType] = valueBox
      def availableOptions: Seq[(Area,String)] = {
        val list = (Area.createRecord.name("Area1") -> "Area1") ::
          (Area.createRecord.name("Area2") -> "Area2") :: Nil
        list.toSeq
      }
      override def toForm: Box[Elem] = {
        tryo(SHtml.selectObj(availableOptions, defaultValueBox, s => {
          set(s)
        }))
      }
  }
  object program extends ObjectIdRefField(this, Program)
  object productiveUnit extends ObjectIdRefField(this, ProductiveUnit){
    override def optional_? = true
  }
  object city extends ObjectIdRefField(this, City)
  object country extends ObjectIdRefField(this, Country)
  object place extends StringField(this, "")
  object shortDescription extends TextareaField(this, 1000)
  object activities extends ObjectIdRefListField(this, Activity)
  object description extends TextareaField(this, 1000)
  object requirements extends ObjectIdRefListField(this, EventRequirement)

  object expositors extends ObjectIdRefListField(this, User)
  object organizers extends ObjectIdRefListField(this, User)
  object handlers extends ObjectIdRefListField(this, User)
  object sponsors extends ObjectIdRefListField(this, User)
  object supports extends ObjectIdRefListField(this, User)
  object collaborators extends ObjectIdRefListField(this, User)
  object pressRoom extends ObjectIdRefField(this, PressNotes)

  object goal extends StringField(this, "")
  object quote extends StringField(this, "")
  object tools extends StringField(this, "")
  object supplies extends StringField(this, "")
  object registration extends StringField(this, "")

  object costContributionByUse extends ObjectIdRefField(this, CostContributionByUse)
  //object schedule extends MongoListField[Event, Schedule](this)
  object process extends ObjectIdRefField(this, Process)
  object actionLines extends ObjectIdRefListField(this, ActionLine)
}

object Event extends Event with RogueMetaRecord[Event]