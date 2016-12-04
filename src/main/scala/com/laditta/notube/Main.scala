package com.laditta.notube

import com.google.api.services.youtube.YouTube
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import java.sql.Timestamp
import java.util

import com.google.api.client.http.{HttpRequest, HttpRequestInitializer}
import com.google.api.services.youtube.model.{SearchListResponse, SearchResult}

import scala.io.Source
import scala.util.Random
import scala.collection.JavaConversions._

object Main extends App with RandomDate {

  val offset: Timestamp = Timestamp.valueOf("2012-01-01 00:00:00")
  val end: Timestamp = Timestamp.valueOf("2013-01-01 00:00:00")


  val NUMBER_OF_VIDEOS_RETURNED = 25L

  val credentials = Source.fromInputStream(getClass.getResourceAsStream("/api_key.txt"), "utf-8").getLines().toList.head

  val youTube = new YouTube.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance, new HttpRequestInitializer() {
    override def initialize(request: HttpRequest): Unit = ()
  }).setApplicationName("notube-search").build()

  //  Random.

  val StartStop(start, stop) = get10MinsInterval()

  val search = youTube.search().list("id")
    .setKey(credentials)
    .setType("video")
    .setMaxResults(NUMBER_OF_VIDEOS_RETURNED.toLong)
    .setPublishedAfter(DateTime.parseRfc3339(start.toLocalDateTime.toString))
    .setPublishedBefore(DateTime.parseRfc3339(stop.toLocalDateTime.toString))

  val result: SearchListResponse = search.execute()

  val items: List[SearchResult] = result.getItems.toList

  items.map{_.getId.getVideoId}.foreach(id => println(s"https://www.youtube.com/watch?v=$id"))

}

trait RandomDate {

  val offset: Timestamp
  val end: Timestamp
  private lazy val diff = end.getTime - offset.getTime + 1

  val tenMinutes = 1000L * 60 * 10

  def getRandomDate() = {
    new Timestamp(offset.getTime + (Random.nextDouble() * diff).toLong)
  }

  def get10MinsInterval() = {
    val start = getRandomDate()
    val end = new Timestamp(start.getTime + tenMinutes)

    StartStop(start, end)
  }


    case class StartStop(start: Timestamp, stop: Timestamp)

}
