package me.rerun.akkanotes.lifecycle

import akka.actor.{ActorLogging, Actor}
import akka.event.LoggingReceive

class BasicLifecycleLoggingActor extends Actor with ActorLogging{

  log.info ("Inside BasicLifecycleLoggingActor Constructor")
  log.info (context.self.toString())
  //context.stop(self)

  override def preStart() ={
    log.info("Inside the preStart method of BasicLifecycleLoggingActor")
  }

  def receive = LoggingReceive{
    case "hello" => {
      Thread.sleep(3000)
      log.info ("hello")
    }
    case "stop" => context.stop(self)
  }

  override def postStop()={
    log.info ("Inside postStop method of BasicLifecycleLoggingActor")
  }

}