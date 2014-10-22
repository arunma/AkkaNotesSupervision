package me.rerun.akkanotes.lifecycle

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.StudentProtocol.InitSignal
import me.rerun.akkanotes.supervision.StudentActor

/**
 * @author Arun
 */
object SupervisionScratchpad extends App{

  val actorSystem=ActorSystem("SupervisionActorSystem")
  /*val actorRef=actorSystem.actorOf(Props[BasicLifecycleLoggingTeacherActor], "basicLifecycleActor")
  //val actorRef2=actorSystem.actorOf(Props[RogueTeacherActor])
  println (actorRef.path)                         
                                                  
  println ("hello")                               
                                                  
  
  val actorRefFromPath=actorSystem.actorSelection("akka://SupervisionActorSystem/user/basicLifecycleActor")
  actorRefFromPath!QuoteRequest*/
  
  val teacherRef=actorSystem.actorOf(Props[BasicLifecycleLoggingActor], "teacherActor")
  val studentActor=actorSystem.actorOf(Props(new StudentActor(teacherRef)),"studentActor")
  studentActor!InitSignal
  
  
  

}
