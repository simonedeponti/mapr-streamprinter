name := "mapr-streamprinter"

version := "0.1"

scalaVersion := "2.12.6"

mainClass in (Compile, packageBin) := Some("com.github.simonedeponti.maprstreamprinter.MaprStreamPrinter")

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.9.0.0-mapr-1710" % "provided"

resolvers += "MapR releases" at "http://repository.mapr.com/nexus/content/repositories/releases"