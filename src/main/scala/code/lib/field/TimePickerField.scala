package code.lib.field

import code.model.OfficeHours
import net.liftmodules.extras.SnippetHelper
import net.liftweb.common._
import net.liftweb.http.S
import net.liftweb.http.js.JsCmds.Run
import net.liftweb.mongodb.record.BsonRecord
import net.liftweb.mongodb.record.field.BsonRecordField
import net.liftweb.util.Helpers._


class TimePickerField[OwnerType <: BsonRecord[OwnerType]](rec: OwnerType)
  extends BsonRecordField(rec, OfficeHours) with SnippetHelper {

  def isAutoFocus: Boolean = false

  def setFromTimePicker(in: String) = {
    println("TIMEPICKER:"+in)
  }

  override def toString = valueBox.dmap("08:00")(s => s"${s.hours.get}:${s.minutes.get}")

  private def elem = {
    val fieldId: String = uniqueFieldId.openOr(randomString(12))
    val js = Run(
      "$('#" + fieldId + "').timepicker();" ++ s"$$('#$fieldId').on('click', function(ev) { $$('#$fieldId').timepicker('showWidget')});")

    S.appendJs(js)
    S.fmapFunc(S.SFuncHolder(this.setFromTimePicker(_))) {
      funcName =>
        <div class="col-md-12">
          {<input type={formInputType}
                  id={fieldId}
                  name={funcName}
                  value={toString}
                  tabindex={tabIndex.toString}
                  class="form-control timepicker" /> % autofocus(isAutoFocus)
          }
          <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
        </div>
    }
  }

  override def toForm = Full(elem)

}