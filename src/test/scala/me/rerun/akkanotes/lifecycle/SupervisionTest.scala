package me.rerun.akkanotes.lifecycle

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}
import me.rerun.akkanotes.supervision.TeacherActorAllForOne

class SupervisionTest extends TestKit(ActorSystem("TestUniversityMessageSystem", ConfigFactory.parseString("""
                                            akka{
                                              loggers = ["akka.testkit.TestEventListener"]
                                              test{
                                                  filter-leeway = 7s
                                              }
                                            }
                                    """)))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll
  with ImplicitSender {

  "A  teacher" must {
    "print logs from constructor, preStart methods  when an actorOf is being called" in {
      system.actorOf(Props[TeacherActorAllForOne], "teacherActor")
    }

  }

  override def afterAll() {
    super.afterAll()
    system.shutdown()
    Thread.sleep(2000);
  }

}