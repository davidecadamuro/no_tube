package com.laditta.notube

import java.sql.Timestamp

import com.google.api.client.util.DateTime
import org.scalatest.{FlatSpec, Matchers}

class RandomDateSpec extends FlatSpec with Matchers with RandomDate {

  val offset: Timestamp = Timestamp.valueOf("2012-01-01 00:00:00")
  val end: Timestamp = Timestamp.valueOf("2013-01-01 00:00:00")

  "RandomDate" should "create a timestamp in the given interval" in {
    val random = getRandomDate()

    println(random.toLocalDateTime)

    random.compareTo(offset) should be >= 0
    random.compareTo(end) should be <= 0
  }

  it should "create a ten minutes interval" in {
    val interval = get10MinsInterval()

    interval.stop.getTime - interval.start.getTime shouldBe 1000 * 60 * 10
  }

  it should "create an object readable by the DateTime of Google" in {
    noException should be thrownBy DateTime.parseRfc3339(getRandomDate.toLocalDateTime.toString)
  }


}
