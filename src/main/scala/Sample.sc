import akka.actor.{ActorSystem, Props}
import com.typesafe.config._
import me.rerun.akkanotes.lifecycle.BasicLifecycleLoggingActor
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest


val actorSystem=ActorSystem("SupervisionActorSystem", ConfigFactory.load("file:///Users/Gabriel/Projects/ScalaProjects/AkkaNotesSupervision/src/main/resources/application.conf"))
val actorRef=actorSystem.actorOf(Props[BasicLifecycleLoggingActor], "basicLifecycleActor")
println (actorRef.path)
println ("hello")
val actorRefFromPath=actorSystem.actorSelection("akka://SupervisionActorSystem/user/basicLifecycleActor")
actorRefFromPath!QuoteRequest

