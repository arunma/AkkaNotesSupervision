
name := """AkkaNotesActorSupervision"""

version := "2.3.5"

scalaVersion := "2.11.2"

resolvers ++=
        Seq("repo" at "http://repo.typesafe.com/typesafe/releases/")

libraryDependencies ++= {
	val akkaVersion = "2.3.4"
	Seq(
  		"com.typesafe.akka" %% "akka-actor" % "2.3.4",
  		"com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  		"ch.qos.logback" % "logback-classic" % "1.1.2",
  		"com.typesafe.akka" %% "akka-testkit" % akkaVersion, 
  		"org.scalatest" %% "scalatest" % "2.2.0"
		)
}

