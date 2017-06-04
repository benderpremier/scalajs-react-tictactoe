package components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

/**
  * Created by bender on 01.06.17.
  */
object HelloComponent {

  case class Props(/* TODO */)

  val helloComponent = ScalaComponent.builder[Unit]("HelloComponent")
    .renderStatic(<.div("Hello from within a component"))
    .build


}
