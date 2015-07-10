package code.model

import code.config.Site
import code.lib.{BaseModel, RogueMetaRecord}
import code.lib.field.{BsStringField, BsCkTextareaField}
import net.liftweb.common.{Box, Full}
import net.liftweb.http.SHtml
import net.liftweb.mongodb.record.MongoRecord
import net.liftweb.mongodb.record.field.{ObjectIdPk, ObjectIdRefField}
import net.liftweb.record.field.{EnumNameField, StringField, TextareaField}

class Process private () extends MongoRecord[Process] with ObjectIdPk[Process] with BaseModel[Process] {

  override def meta = Process

  def title = "Proceso"

  def entityListUrl = Site.backendProcess.menu.loc.calcDefaultHref

  object name extends BsStringField(this, 500) {
    override def displayName = "Nombre"
  }

  object goal extends BsCkTextareaField(this, 1000) {
    override def displayName = "Objetivo"
  }

  object description extends BsCkTextareaField(this, 1000) {
    override def displayName = "Descripción"
  }

  object administrator extends ObjectIdRefField(this, User) {
    override def displayName = "Coordinador"
    override def toString = {
      obj.dmap("")(_.name.get)
    }
    val listUsers = User.findAll
    override def toForm = {
      Full(SHtml.selectElem(
        listUsers,
        User.currentUser,
        "class" -> "select2 form-control",
        "data-placeholder" -> "Seleccione administrador.."
      )(u => set(u.id.get)))
    }
  }

  object area extends ObjectIdRefField(this, Area) {
    override def displayName = "Área"
    override def optional_? = true
    override def toString = obj.dmap("")(_.name.get)
    val listAreas = Area.findAll
    override def toForm = {
      Full(SHtml.selectElem(
        listAreas,
        obj,
        "class" -> "select2 form-control",
        "data-placeholder" -> "Seleccione area.."
      )(a => set(a.id.get)))
    }
  }

  object program extends ObjectIdRefField(this, Program) {
    override def displayName = "Programa"
    override def optional_? = true
    override def toString = obj.dmap("")(_.name.get)
    val listProgram = Program.findAll
    override def toForm = {
      Full(SHtml.selectElem(
        listProgram,
        obj,
        "class" -> "select2 form-control",
        "data-placeholder" -> "Seleccione program.."
      )(s => set(s.id.get)))
    }
  }

  object processType extends EnumNameField(this, ProcessType) {
    override def displayName = "Tipo"
    override def toForm =
      Full(SHtml.selectObj[Box[ProcessType.Value]](
        buildDisplayList,
        Full(valueBox),
        s => setBox(s),
        "class" -> "select2 form-control",
        "data-placeholder" -> "Seleccione tipo.."
      ))
  }

  object history extends BsCkTextareaField(this, 1000) {
    override def displayName = "Historia"
  }

  override def toString = name.get
}

object Process extends Process with RogueMetaRecord[Process] {
  override def fieldOrder = List(name, processType, goal, description, area, program, administrator, history)
}

object ProcessType extends Enumeration {
  type ProcessType = Value
  val Intern = Value("Interno")
  val Associate = Value("Asociado")
}