package components

/**
  * Created by bender on 03.06.17.
  */
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object SquareComponent {

  final case class Props(value: String, onClick: Callback) {
    @inline def render: VdomElement = Component(this)
  }

  //implicit val reusabilityProps: Reusability[Props] =
  //  Reusability.caseClass



  final class Backend($: BackendScope[Props, Unit]) {


    def render(p: Props): VdomElement =
      <.button(
        ^.cls := "square",
        p.value,
        ^.onClick --> p.onClick
      )
  }

  val Component = ScalaComponent.builder[Props]("SquareComponent")
    .renderBackend[Backend]
    //.configure(Reusability.shouldComponentUpdate)
    .build


  def apply(value: String, onClick: Callback) = Component(Props(value,onClick))
}
