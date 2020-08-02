package http

import scalaj.http._

object Example {
  def run(): Unit = {
    val response = Http("https://xkcd.com/614/info.0.json").asParamMap
    println(response)
  }
}