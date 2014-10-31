package me.rerun.akkanotes.supervision

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.SupervisorStrategy.Stop

object OtherExceptionApp extends App{

  val actorSystem=ActorSystem("OtherExceptionSystem")
  val actor=actorSystem.actorOf(Props[OtherExceptionParentActor])
  actor!"create_child"
  
}

class OtherExceptionParentActor extends Actor with ActorLogging{
  
  def receive={
    case "create_child"=> {
      log.info ("creating child")
      val child=context.actorOf(Props[OtherExceptionChildActor])
      
      child!"throwSomeException"
      child!"someMessage"
    }
  }
}

class OtherExceptionChildActor extends akka.actor.Actor with ActorLogging{
  
  override def preStart={
    log.info ("Starting Child Actor")
  }
  
  def receive={
    case "throwSomeException"=> {
      throw new Exception ("I'm getting thrown for no reason") 
    }
    case "someMessage" => log.info ("Restarted and printing some Message")
  }
  
  override def postStop={
    log.info ("Stopping Child Actor")
  }
  
}