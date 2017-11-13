name := "slick_airport"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.postgresql" % "postgresql" % "42.1.4"
  //,
//  "org.joda" % "joda-convert" % "1.8.1",
 // "joda-time" % "joda-time" % "2.9.9"
)