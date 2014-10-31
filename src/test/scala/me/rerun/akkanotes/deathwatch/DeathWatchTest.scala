package me.rerun.akkanotes.deathwatch

import akka.actor.{Terminated, ActorSystem, Props}
import akka.testkit._
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}
import me.rerun.akkanotes.lifecycle.BasicLifecycleLoggingActor
import me.rerun.akkanotes.protocols.QuoteRepositoryProtocol._
import me.rerun.akkanotes.protocols.TeacherProtocol.{QuoteRequest, QuoteResponse}
import scala.concurrent.duration._
import me.rerun.akkanotes.protocols.QuoteRepositoryProtocol.QuoteRepositoryRequest
import me.rerun.akkanotes.protocols.QuoteRepositoryProtocol.QuoteRepositoryResponse

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

  "A QuoteRepositoryActor" must {
    "send back a quote response for 3 times without any error" in {
      val quoteRepository=TestActorRef[QuoteRepositoryActor]

      within (1000 millis) {
        var receivedQuotes = List[String]()
        (1 to 3).foreach(_ => quoteRepository ! QuoteRepositoryRequest)

        receiveWhile() {
          case QuoteRepositoryResponse(quoteString) => {
            receivedQuotes = receivedQuotes :+ quoteString
          }
        }
        println(s"receiveCount ${receivedQuotes.size}")
        receivedQuotes.size must be (3)
      }
    }


    "send back a termination message to the watcher on 4th message" in {
      val quoteRepository=TestActorRef[QuoteRepositoryActor]

      val testProbe=TestProbe()
      testProbe.watch(quoteRepository) //Let's watch the Actor

      within (1000 millis) {
        var receivedQuotes = List[String]()
        (1 to 3).foreach(_ => quoteRepository ! QuoteRepositoryRequest)
        receiveWhile() {
          case QuoteRepositoryResponse(quoteString) => {
            receivedQuotes = receivedQuotes :+ quoteString
          }
        }

        receivedQuotes.size must be (3)
        println(s"receiveCount ${receivedQuotes.size}")

        //4th message
        quoteRepository!QuoteRepositoryRequest
        testProbe.expectTerminated(quoteRepository)  //Expect a Terminated Message
      }
    }

    "not send back a termination message on 4th message if not watched" in {
      val quoteRepository=TestActorRef[QuoteRepositoryActor]

      val testProbe=TestProbe()
      testProbe.watch(quoteRepository) //watching

      within (1000 millis) {
        var receivedQuotes = List[String]()
        (1 to 3).foreach(_ => quoteRepository ! QuoteRepositoryRequest)
        receiveWhile() {
          case QuoteRepositoryResponse(quoteString) => {
            receivedQuotes = receivedQuotes :+ quoteString
          }
        }

        testProbe.unwatch(quoteRepository) //not watching anymore
        receivedQuotes.size must be (3)
        println(s"receiveCount ${receivedQuotes.size}")

        //4th message
        quoteRepository!QuoteRepositoryRequest
        testProbe.expectNoMsg() //Not Watching. No Terminated Message
      }
    }

    "end back a termination message to the watcher on 4th message to the TeacherActor" in {

      //This just subscribes to the EventFilter for messages. We have asserted all that we need against the QuoteRepositoryActor in the previous testcase
      val teacherActor=TestActorRef[TeacherActorWatcher]

      within (1000 millis) {
        (1 to 3).foreach (_=>teacherActor!QuoteRequest) //this sends a message to the QuoteRepositoryActor

        EventFilter.error (pattern="""Child Actor .* Terminated""", occurrences = 1).intercept{
          teacherActor!QuoteRequest //Send the dangerous 4th message
        }
      }
    }
  }

  override def afterAll() {
    super.afterAll()
    TestKit.shutdownActorSystem(system)
    Thread.sleep(2000);
  }

}