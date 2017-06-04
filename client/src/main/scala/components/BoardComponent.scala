package components

/**
  * Created by bender on 03.06.17.
  */
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.vdom.html_<^._

object BoardComponent {

  final case class Props() {
    @inline def render: VdomElement = Component(this)
  }

  final case class State(squares: Vector[String], xIsNext: Boolean, hasWinner: Boolean)

  object State {
    def init: State =
      State((1 to 9).toVector.map(_ => ""),true, false)
  }

  //implicit val reusabilityProps: Reusability[Props] =
  //  Reusability.caseClass

  final class Backend($: BackendScope[Props, State]) {
    def hasWinner(squares: Vector[String]): Boolean = {
      val winnerLines = List((0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6))
      for (line <- winnerLines) {
        if ((squares(line._1)!= "") && (squares(line._1) == squares(line._2)) && (squares(line._1) == squares(line._3))) return true
      }
      return false
    }

    def handleClick(i: Int) : Callback =
      $.modState(s => {
        // check if we already have a winner we don't do nothing
        if(s.hasWinner) s
        // check if the square was already clicked we don't do anything
        else if (s.squares(i) != "") s
        // Finally if it's not the case we can update the state and check for a winner
        else {
          val newSquare = s.squares.updated(i,if (s.xIsNext) "X" else "O")
          if (hasWinner(newSquare)) {
            State(newSquare, s.xIsNext, true)
          }else{
            State(newSquare, !s.xIsNext, false)
          }
        }
      })

    def renderSquare(i: Int, state: State) = {
      SquareComponent(
        state.squares(i),
        handleClick(i)
      )
    }

    def render(p: Props, s: State): VdomElement =
      <.div(
        <.div(
          ^.cls := "status",
          if(s.hasWinner){
            s"Winner: ${ if (s.xIsNext) "X" else "O"}"
          } else {
            s"Next Player: ${ if (s.xIsNext) "X" else "O"}"
          }
        ),
        <.div(
          ^.cls := "board-row",
          renderSquare(0,s),
          renderSquare(1,s),
          renderSquare(2,s)
        ),
        <.div(
          ^.cls := "board-row",
          renderSquare(3,s),
          renderSquare(4,s),
          renderSquare(5,s)
        ),
        <.div(
          ^.cls := "board-row",
          renderSquare(6,s),
          renderSquare(7,s),
          renderSquare(8,s)
        )
      )
  }

  val Component = ScalaComponent.builder[Props]("Board")
    .initialState(State.init)
    .renderBackend[Backend]
    //.configure(Reusability.shouldComponentUpdate)
    .build

  def apply() = Component(Props())
}
