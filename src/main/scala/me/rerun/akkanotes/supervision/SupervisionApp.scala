package me.rerun.akkanotes.supervision

import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest
import akka.actor.Actor
import akka.actor.ActorLogging

/**
 * @author Arun
 */
object SupervisionApp extends App{

  val actorSystem=ActorSystem("SupervisionActorSystem")
  val teacherProps=Props[TeacherActorAllForOne]
  val teacherSupervisor=actorSystem.actorOf(Props(new DepartmentSupervisor(teacherProps)),"teacherSupervisor")
  //println ("Teacher Supervisor path : "+ teacherSupervisor.path)
  teacherSupervisor!QuoteRequest


}

