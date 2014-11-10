package me.rerun.akkanotes.supervision

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import akka.actor.Actor
import akka.actor.ActorLogging

object ActorInitializationExceptionApp extends App{

  val actorSystem=ActorSystem("ActorInitializationException")
  val actor=actorSystem.actorOf(Props[ActorInitializationExceptionActor], "initializationExceptionActor")
  actor!"someMessageThatWillGoToDeadLetter"
}

class ActorInitializationExceptionActor extends Actor with ActorLogging{
  override def preStart={
    throw new Exception("Some random exception")
  }
  def receive={
    case _=>
  }
}
