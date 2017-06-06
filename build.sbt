val client =
  project.in(file("client"))
    .enablePlugins(ScalaJSBundlerPlugin, ScalaJSPlugin, ScalaJSWeb)
    .settings(
      scalaVersion := "2.12.2",
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2",
      libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "1.0.0",
      npmDependencies in Compile ++= Seq(
        "react" -> "15.5.4",
        "react-dom" -> "15.5.4"
      ),
      npmDevDependencies in Compile += "expose-loader" -> "0.7.1"
    )

val server =
  project.in(file("server"))
    .enablePlugins(SbtWeb, WebScalaJSBundlerPlugin, WorkbenchPlugin)
    .settings(
      scalaVersion := "2.12.2",
      scalaJSProjects := Seq(client),
      pipelineStages in Assets := Seq(scalaJSPipeline),
      // triggers scalaJSPipeline when using compile or continuous compilation
      compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value
    )

val reactJSScalaJS =
  project.in(file("."))
    .aggregate(client, server)
    .settings(
      name := "react-tictactoe",
      version := "1.0"
    )