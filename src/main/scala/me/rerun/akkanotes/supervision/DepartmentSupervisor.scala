package me.rerun.akkanotes.supervision

import akka.actor.{Actor, ActorLogging, Props}
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest

/**
 * @author Arun Manivannan
 */
class DepartmentSupervisor (teacherProps:Props) extends Actor with ActorLogging {

  val teacherActor=context.actorOf(teacherProps, "teacherActor")
  log.info ("Teacher Actor path : "+ teacherActor.path)
  
  
  def receive = {

    case qr@QuoteRequest=>{
      teacherActor.forward(qr)
    }

  }
}
