package me.rerun.akkanotes.lifecycle

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.DeadLetter
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.actor.Kill
import akka.actor.PoisonPill

object LifecycleApp extends App {

  val actorSystem = ActorSystem("LifecycleActorSystem")
  val lifecycleActor = actorSystem.actorOf(Props[BasicLifecycleLoggingActor], "lifecycleActor")

  val deadLetterListener = actorSystem.actorOf(Props[MyCustomDeadLetterListener])
  actorSystem.eventStream.subscribe(deadLetterListener, classOf[DeadLetter])

  lifecycleActor ! "hello"
  lifecycleActor ! "hello"
  //lifecycleActor ! "stop"
  lifecycleActor!Kill
  lifecycleActor ! "hello"
  lifecycleActor ! "hello"
  lifecycleActor ! "stop"
  //lifecycleActor ! Kill
  lifecycleActor ! "hello"

}

class MyCustomDeadLetterListener extends Actor {  
  def receive = {
    case deadLetter: DeadLetter => println(s"FROM CUSTOM LISTENER $deadLetter")
  }
}