package me.rerun.akkanotes.supervision

import akka.actor.{Actor, ActorLogging, ActorRef}
import me.rerun.akkanotes.protocols.StudentProtocol.InitSignal
import me.rerun.akkanotes.protocols.TeacherProtocol.{QuoteRequest, QuoteResponse}

/*
 * The Student Actor class. 
 * 
 */

class StudentActor (teacherActorRef:ActorRef) extends Actor with ActorLogging {

  def receive = {

    /*
     * This InitSignal is received from the DriverApp. 
     * On receipt, the Student sends a message to the Teacher actor. 
     * The teacher actor on receipt of the QuoteRequest responds with a QuoteResponse 
     */
    case InitSignal=> {
      //teacherActorRef!QuoteRequest
      val teacherActorRefFromSelection=context.system.actorSelection("user/teacherActor")
      teacherActorRefFromSelection!QuoteRequest
    }
    
    /*
     * The Student simply logs the quote received from the TeacherA˙ctor
     * 
     */
    case QuoteResponse(quoteString) => {
      log.info ("Received QuoteResponse from Teacher")
      log.info(s"Printing from Student Actor $quoteString")
    }

  }

}