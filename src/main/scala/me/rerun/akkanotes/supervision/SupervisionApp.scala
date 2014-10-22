package me.rerun.akkanotes.supervision

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import me.rerun.akkanotes.supervision.TeacherActor

/**
 * @author Arun
 */
object SupervisionApp extends App{

  val actorSystem=ActorSystem("SupervisionActorSystem")
  val teacherProps=Props[TeacherActor]
  val teacherSupervisor=actorSystem.actorOf(Props(new TeacherSupervisor(teacherProps)),"teacherSupervisor")

  println ("Teacher Supervisor path : "+ teacherSupervisor.path)
  teacherSupervisor!QuoteRequest


}
