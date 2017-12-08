name := "dxf_comb"

version := "0.1"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-target:jvm-1.6"
  , "-deprecation"
  , "-feature"
  , "-unchecked"
  , "-Xfatal-warnings"
  , "-Xlint"
  , "-Ywarn-numeric-widen"
  , "-Ywarn-value-discard"
  , "-Yno-adapted-args"
  , "-Ywarn-dead-code"
)
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "latest.integration" withSources() withJavadoc()

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "latest.integration" % "test" withSources() withJavadoc()

libraryDependencies += "commons-io" % "commons-io" % "2.4"

mainClass in assembly := Some("local.dxf.cli.ConvertToXmlFromFile")

assemblyOutputPath in assembly := file("bin/dxf_parser.jar")