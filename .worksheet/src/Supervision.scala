import akka.actor.{ActorSystem, Props}
import me.rerun.akkanotes.lifecycle.BasicLifecycleLoggingActor
import me.rerun.akkanotes.protocols.TeacherProtocol.QuoteRequest

object Supervision{;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(246); 

  val actorSystem=ActorSystem("SupervisionActorSystem");System.out.println("""actorSystem  : akka.actor.ActorSystem = """ + $show(actorSystem ));$skip(93); 
  val actorRef=actorSystem.actorOf(Props[BasicLifecycleLoggingActor], "basicLifecycleActor");System.out.println("""actorRef  : akka.actor.ActorRef = """ + $show(actorRef ));$skip(26); 
  println (actorRef.path);$skip(20); 
  println ("hello");$skip(108); 
  val actorRefFromPath=actorSystem.actorSelection("akka://SupervisionActorSystem/user/basicLifecycleActor");System.out.println("""actorRefFromPath  : akka.actor.ActorSelection = """ + $show(actorRefFromPath ));$skip(32); 
  actorRefFromPath!QuoteRequest}
  
}
