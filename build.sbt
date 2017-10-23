name := "mapr-streamprinter"

version := "0.1"

scalaVersion := "2.12.4"

mainClass in (Compile, packageBin) := Some("com.github.simonedeponti.maprstreamprinter.MaprStreamPrinter")

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.9.0.0-mapr-1607-streams-5.2.0" % "provided"

resolvers += "MapR releases" at "http://repository.mapr.com/nexus/content/repositories/releases"