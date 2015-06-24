package code
package model
package resource

import code.lib.RogueMetaRecord
import net.liftweb.common.Full
import net.liftweb.http.{S, SHtml}
import net.liftweb.http.js.JsCmds._
import net.liftweb.record.field.{TextareaField, StringField}
import code.lib.field._
import xml.{Elem, NodeSeq, Text}

class ConcreteResource private() extends Resource[ConcreteResource] {

  override def meta = ConcreteResource
}

object ConcreteResource extends ConcreteResource with RogueMetaRecord[ConcreteResource] {

  override def collectionName = "resource"
}
