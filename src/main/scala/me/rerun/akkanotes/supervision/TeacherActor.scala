package me.rerun.akkanotes.supervision

import akka.actor.{Actor, ActorLogging, actorRef2Scala}
import me.rerun.akkanotes.protocols.TeacherProtocol.{QuoteRequest, QuoteResponse}

class TeacherActor extends Actor with ActorLogging {

  val quotes = List(
    "Moderation is for cowards",
    "Anything worth doing is worth overdoing",
    "The trouble is you think you have time",
    "You never gonna know if you never even try")

  def receive = {

    case QuoteRequest => {

      import scala.util.Random

      //Get a random Quote from the list and construct a response
      val quoteResponse = QuoteResponse(quotes(Random.nextInt(quotes.size)))

      log.info ("QuoteRequest received. Sending response to Student")
      //respond back to the Student who is the original sender of QuoteRequest
      sender ! quoteResponse

    }

  }

}