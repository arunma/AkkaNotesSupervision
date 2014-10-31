package me.rerun.akkanotes.supervision

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Kill
import akka.actor.PoisonPill
import akka.actor.Terminated

object DeathPactExceptionApp extends App{

  val actorSystem=ActorSystem("DeathPactExceptionSystem")
  val actor=actorSystem.actorOf(Props[DeathPactExceptionParentActor])
  actor!"create_child" //Throws DeathPactException
  Thread.sleep(2000) //Wait until Stopped
  actor!"someMessage" //Message goes to DeadLetters
  
}

class DeathPactExceptionParentActor extends Actor with ActorLogging{
  
  def receive={
    case "create_child"=> {
      log.info ("creating child")
      val child=context.actorOf(Props[DeathPactExceptionChildActor])
      context.watch(child) //Watches but doesnt handle terminated message. Throwing DeathPactException here.
      child!"stop"
    }
    case "someMessage" => log.info ("some message")
    //Doesnt handle terminated message
    //case Terminated(_) =>
  }
}

class DeathPactExceptionChildActor extends Actor with ActorLogging{
  def receive={
    case "stop"=> {
      log.info ("Actor going to stop and announce that it's terminated")
      self!PoisonPill
    }
  }
}