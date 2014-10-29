package me.rerun.akkanotes.supervision

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest

/**
 * @author Arun
 */
object SupervisionApp extends App{

  val actorSystem=ActorSystem("SupervisionActorSystem")
  val teacherProps=Props[TeacherActorAllForOne]
  val teacherSupervisor=actorSystem.actorOf(Props(new DepartmentSupervisor(teacherProps)),"teacherSupervisor")

  println ("Teacher Supervisor path : "+ teacherSupervisor.path)
  teacherSupervisor!QuoteRequest


}
