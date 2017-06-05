package components

/**
  * Created by bender on 03.06.17.
  */
import components.GameComponent.Squares
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._


object BoardComponent {

  final case class Props(currentSquares: Squares, onclick: Int => Callback) {
    @inline def render: VdomElement = Component(this)
  }

//  final case class State(squares: Vector[String], xIsNext: Boolean, hasWinner: Boolean)
//
//  object State {
//    def init: State =
//      State((1 to 9).toVector.map(_ => ""),true, false)
//  }

  //implicit val reusabilityProps: Reusability[Props] =
  //  Reusability.caseClass

  final class Backend($: BackendScope[Props, Unit]) {


    def renderSquare(i: Int, p: Props) = {
      SquareComponent(
        p.currentSquares(i),
        p.onclick(i)
      )
    }

    def render(p: Props): VdomElement =
      <.div(
        <.div(
          ^.cls := "board-row",
          renderSquare(0,p),
          renderSquare(1,p),
          renderSquare(2,p)
        ),
        <.div(
          ^.cls := "board-row",
          renderSquare(3,p),
          renderSquare(4,p),
          renderSquare(5,p)
        ),
        <.div(
          ^.cls := "board-row",
          renderSquare(6,p),
          renderSquare(7,p),
          renderSquare(8,p)
        )
      )
  }

  val Component = ScalaComponent.builder[Props]("Board")
    .renderBackend[Backend]
    //.configure(Reusability.shouldComponentUpdate)
    .build

  def apply(currentSquares: Squares, onClick: Int => Callback) = Component(Props(currentSquares, onClick))
}
