package code
package model
package activity

import code.model.Area
import code.model.resource.ClassType._
import code.model.resource.CostType._
import code.model.resource._
import org.joda.time.DateTime
import code.model.event.{Schedule, RangeType, CostInfo}

class ActivitySpec extends BaseMongoSessionWordSpec {

  "Activity" should {
    /*"create, validate, save, and retrieve properly" in {

      val area1 = Area.createRecord
        .code("ARV")
        .description("")
        .name("Visual and graphic design")

      area1.validate.length should equal (0)
      area1.save(false)

      val area2 = Area.createRecord
        .code("LET")
        .description("")
        .name("Arts and Literature")

      area2.validate.length should equal (0)
      area2.save(false)

      val consumer = Consumer.createRecord
        .areas(area1 :: area2 :: Nil)
        .name("associated")
        .description("EMERGING NATIONAL - INTERNATIONAL Autogestionados (40% OFF) *")

      consumer.validate.length should equal (0)
      consumer.save(false)

      val quoteRoom = RoomQuote.createRecord
        .costType(CostType.Day)
        .parameter("10 hrs. week")
        .cost(280.01)
        .characteristics("Weekly (10% OFF)")

        .consumer(consumer.id.get)

      quoteRoom.validate.length should equal (0)
      quoteRoom.save(false)

      val room = Room.createRecord
        .capacity("300")
        .name("Trozadero")
        .classType(RoomType)
        .description("Include information about recent international progress in the field of the research, and the " +
          "relationship of this proposal to work in the field generally")

      val errsRoom = room.validate
      if (errsRoom.length > 1) {
        fail("Validation error: " + errsRoom.mkString(", "))
      }

      room.validate.length should equal (0)
      room.save(false)

      val quotePackage = RoomQuote.createRecord
        .costType(CostType.Day)
        .cost(280.01)
        //.characteristics("Weekly (10% OFF)")
        .parameter("10 hrs. week")
        .consumer(consumer.id.get)

      quotePackage.validate.length should equal (0)
      quotePackage.save(false)

      val packageResourse = ResourcePackage
        .createRecord
        .name("DIGITAL ROOM LAB + 10 PCs + DATA DISPLAY")
        .description("The contributions for use discount is only valid if payment of at least 40% of the contribution " +
          "is made, before the workshop")
        .comboName("PACKAGE FOR WORKSHOPS IN THE DIGITAL LAB")
        .classType(PackageType)

      val errsPack = packageResourse.validate
      if (errsPack.length > 1) {
        fail("Validation error: " + errsPack.mkString(", "))
      }

      packageResourse.validate.length should equal (0)
      packageResourse.save(false)

      val city = createCity("Cochabamba")
      val country = createCountry("Bolivia")
      val schedule = createSchedule

      val activityType = ActivityType
        .createRecord
        .name("exhibition")

      activityType.validate.length should equal (0)
      activityType.save(false)

      val costInfo = createCostInfo

      val date1: DateTime = new DateTime(2015, 3, 10, 15, 0, 0, 0)

      val activity = Activity
        .createRecord
        .activityType(activityType.id.get)
        .costInfo(costInfo.id.get)
        .description("Children's Day")
        .name("Children's Day")
        .rooms(room.id.get :: Nil)
        .packages(packageResourse.id.get :: Nil)
        .date(date1.toDate)

      val errsActivity= activity.validate
      if (errsActivity.length > 1) {
        fail("Validation error: " + errsActivity.mkString(", "))
      }

      activity.validate.length should equal (0)

      activity.save(false)

    }*/
  }

  def createCostInfo: CostInfo = {

    val costInfo = CostInfo
      .createRecord
      .cost(100.00)
      .currency("Bs")
      .description("Para cubrir los costos de herramientas e insumos principalmente. Se aceptan becados.")

    val errorsList = costInfo.validate
    if (errorsList.length > 1) {
      fail("Validation error: " + errorsList.mkString(", "))
    }
    costInfo.validate.length should equal (0)
    costInfo.save(false)
  }


  def createSchedule: Schedule = {

    val date1: DateTime = new DateTime(2015, 3, 10, 15, 0, 0, 0)
    val date2: DateTime = new DateTime(2015, 3, 11, 18, 0, 0, 0)
    val date3: DateTime = new DateTime(2015, 3, 12, 18, 0, 0, 0)
    val date4: DateTime = new DateTime(2015, 3, 18, 21, 0, 0, 0)

    val schedule = Schedule
      .createRecord
      .isAtSameHour(true)
      .dateRange(date1 :: date2 :: date3 :: date4 :: Nil)
      .rangeType(RangeType.ContinuousInterval)


    val errsList = schedule.validate
    if (errsList.length > 1) {
      fail("Validation error: " + errsList.mkString(", "))
    }
    schedule.validate.length should equal (0)
    schedule.save(false)
    schedule
  }


  def createCity(name: String) = {
    val city = City
      .createRecord
      .name(name)

    val errsCity = city.validate
    if (errsCity.length > 1) {
      fail("Validation error: " + errsCity.mkString(", "))
    }

    city.validate.length should equal (0)
    city.save(false)
    city
  }

  def createCountry(name: String) = {
    val country = Country
      .createRecord
      .name(name)

    val errsCountry = country.validate
    if (errsCountry.length > 1) {
      fail("Validation error: " + errsCountry.mkString(", "))
    }

    country.validate.length should equal (0)
    country.save(false)
  }
}
