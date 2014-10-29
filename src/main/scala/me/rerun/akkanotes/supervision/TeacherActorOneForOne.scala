package me.rerun.akkanotes.supervision

import akka.actor.{Props, Actor, ActorLogging}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import me.rerun.akkanotes.protocols.QuoteRepositoryProtocol._
import me.rerun.akkanotes.exception.RepoDownException

class TeacherActorOneForOne extends Actor with ActorLogging {

  val quoteRepositoryActor=context.actorOf(Props[QuoteRepositoryActor], "quoteRepositoryActor")

  var requestCount=0

  def receive = {

    case QuoteRequest => {
      if (requestCount<=3) quoteRepositoryActor ! QuoteRepositoryRequest

      else  throw new RepoDownException("Simulating a dummy exception after 3 requests")//Let's simulate an error
    }

  }


}