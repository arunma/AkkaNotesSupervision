package me.rerun.akkanotes.supervision

import akka.actor.{ Props, Actor, ActorLogging}
import me.rerun.akkanotes.protocols.TeacherProtocol.{ QuoteRequest, QuoteResponse }
import me.rerun.akkanotes.exception.RepoDownException
import akka.actor.AllForOneStrategy
import me.rerun.akkanotes.deathwatch.QuoteRepositoryActor
import akka.actor.SupervisorStrategy._

class TeacherActorAllForOne extends Actor with ActorLogging {

  var requestCount: Int = 0

  val quoteRepository = context.actorOf(Props[QuoteRepositoryActor])

  override val supervisorStrategy = AllForOneStrategy() {

    case _: RepoDownException => Stop
    case _: Exception => Escalate

  }

  def receive = {

    case QuoteRequest => {

      if (requestCount > 3) {
        //Let's simulate an error
        throw new RepoDownException("Simulating a dummy exception after 3 requests")
      } else {

      }

    }

  }

}