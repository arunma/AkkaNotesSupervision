package me.rerun.akkanotes.supervision

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Kill

object ActorKilledExceptionApp extends App{

  val actorSystem=ActorSystem("ActorKilledExceptionSystem")
  val actor=actorSystem.actorOf(Props[ActorKilledExceptionActor])
  actor!"something"
  actor!Kill
  actor!"something else that falls into dead letter queue"
}

class ActorKilledExceptionActor extends Actor with ActorLogging{
  def receive={
    case message:String=> log.info (message)
  }
}
