import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.lifecycle.BasicLifecycleLoggingActor
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest

object Supervision{

  val actorSystem=ActorSystem("SupervisionActorSystem")
  val actorRef=actorSystem.actorOf(Props[BasicLifecycleLoggingActor], "basicLifecycleActor")
  println (actorRef.path)
  println ("hello")
  val actorRefFromPath=actorSystem.actorSelection("akka://SupervisionActorSystem/user/basicLifecycleActor")
  actorRefFromPath!QuoteRequest
  
}