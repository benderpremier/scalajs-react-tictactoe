package components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object GameComponent {

  final case class Props() {
    @inline def render: VdomElement = Component(this)
  }

  //implicit val reusabilityProps: Reusability[Props] =
  //  Reusability.caseClass

  final class Backend($: BackendScope[Props, Unit]) {
    def render(p: Props): VdomElement =
      <.div(
        ^.cls := "game",
        <.div(
          ^.cls := "game-board",
          BoardComponent()
        ),
        <.div(
          ^.cls := "game-info",
          <.div(),
          <.ol()
        )
      )
  }

  val Component = ScalaComponent.builder[Props]("GameComponent")
    .renderBackend[Backend]
    //.configure(Reusability.shouldComponentUpdate)
    .build

  def apply() = Component(Props())
}