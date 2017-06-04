//addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.1")
// Don't need to add sbt-web when we have sbt-web-scalajs
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.4")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.16")
// Allow to use npm directly
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.6.0")
// See https://scalacenter.github.io/scalajs-bundler/getting-started.html
addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.6.0")

addSbtPlugin("com.lihaoyi" % "workbench" % "0.3.0")