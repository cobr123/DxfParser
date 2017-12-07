name := "dxf_comb"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "latest.integration" withSources() withJavadoc()

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "latest.integration" % "test" withSources() withJavadoc()