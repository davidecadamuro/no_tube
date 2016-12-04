name := "NoTubeFinder"
version := "0.1-SNAPSHOT"
scalaVersion := "2.11.8"
libraryDependencies ++= Seq(
  "com.google.apis" % "google-api-services-youtube" % "v3-rev180-1.22.0",
  "org.scalatest"  %% "scalatest"   % "3.0.0" % Test
)
