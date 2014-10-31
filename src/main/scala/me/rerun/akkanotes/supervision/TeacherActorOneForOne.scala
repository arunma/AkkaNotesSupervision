package me.rerun.akkanotes.supervision

import akka.actor.Actor
import akka.actor.ActorKilledException
import akka.actor.ActorLogging
import akka.actor.DeathPactException
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.SupervisorStrategy.Restart
import akka.actor.SupervisorStrategy.Stop
import me.rerun.akkanotes.exception.RepoDownException
import me.rerun.akkanotes.protocols.QuoteRepositoryProtocol.QuoteRepositoryRequest
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest

class TeacherActorOneForOne extends Actor with ActorLogging {

  val quoteRepositoryActor = context.actorOf(Props[QuoteRepositoryExceptionThrowingActor], "quoteRepositoryActor")
  
  override val supervisorStrategy=OneForOneStrategy() {
    
    case _: RepoDownException		 	=> Restart
    case _: Exception                   => Stop
    
  }

  var requestCount = 0

  def receive = {

    case QuoteRequest => {
      if (requestCount <= 3) quoteRepositoryActor ! QuoteRepositoryRequest

      else throw new RepoDownException("Simulating a dummy exception after 3 requests") //Let's simulate an error
    }

  }

}