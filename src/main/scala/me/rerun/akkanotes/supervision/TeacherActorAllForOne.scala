package me.rerun.akkanotes.supervision

import akka.actor.{Props, Actor, ActorLogging, actorRef2Scala}
import me.rerun.akkanotes.protocols.TeacherProtocol.{QuoteRequest, QuoteResponse}
import me.rerun.akkanotes.exception.RepoDownException

class TeacherActorAllForOne extends Actor with ActorLogging {

  var requestCount:Int=0

  val quoteRepository = context.actorOf(Props[QuoteRepositoryActor])



  def receive = {

    case QuoteRequest => {

      if (requestCount>3) {
        //Let's simulate an error
        throw new RepoDownException("Simulating a dummy exception after 3 requests")
      }
      else{


      }

    }

  }

}