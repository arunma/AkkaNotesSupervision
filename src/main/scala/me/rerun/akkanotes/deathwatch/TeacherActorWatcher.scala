package me.rerun.akkanotes.deathwatch

import akka.actor.{Terminated, Props, Actor, ActorLogging}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import me.rerun.akkanotes.protocols.QuoteRepositoryProtocol.QuoteRepositoryRequest

class TeacherActorWatcher extends Actor with ActorLogging {

  val quoteRepositoryActor=context.actorOf(Props[QuoteRepositoryActor], "quoteRepositoryActor")
  context.watch(quoteRepositoryActor)


  def receive = {
    case QuoteRequest => {
      quoteRepositoryActor ! QuoteRepositoryRequest
    }
    case Terminated(terminatedActorRef)=>{
      log.error(s"Child Actor {$terminatedActorRef} Terminated")
    }
  }
}