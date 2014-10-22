package me.rerun.akkanotes.lifecycle

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

class DeathWatchTest extends TestKit(ActorSystem("TestUniversityMessageSystem", ConfigFactory.parseString("""
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

  "A basic lifecycle teacher" must {
    "print logs from constructor, preStart methods  when an actorOf is being called" in {
      system.actorOf(Props[BasicLifecycleLoggingActor], "teacherActor")
    }

  }

  override def afterAll() {
    super.afterAll()
    system.shutdown()
    Thread.sleep(2000);
  }

}