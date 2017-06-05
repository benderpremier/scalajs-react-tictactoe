package components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.History

object GameComponent {

  final case class Props() {
    @inline def render: VdomElement = Component(this)
  }

  type Squares = Vector[String]

  final case class State(currentSquares: Squares, gameHistory:Vector[Squares], xIsNext: Boolean, hasWinner: Boolean, step: Int)

  object State {
    def initialSquares = (1 to 9).toVector.map(_ => "")
    def init: State =
      State(initialSquares, Vector.empty, true, false, 0)
  }

  final class Backend($: BackendScope[Props, State]) {

    def hasWinner(squares: Squares): Boolean = {
      val winnerLines = List((0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6))
      for (line <- winnerLines) {
        if ((squares(line._1)!= "") && (squares(line._1) == squares(line._2)) && (squares(line._1) == squares(line._3))) return true
      }
      return false
    }

    def handleClick: Int => Callback = (i :Int) =>
      $.modState(s => {
        // check if we already have a winner we don't do nothing
        if(s.hasWinner) s
        // check if the square was already clicked we don't do anything
        else if (s.currentSquares(i) != "") s
        // Finally if it's not the case we can update the state and check for a winner
        else {
          val newSquare = s.currentSquares.updated(i,if (s.xIsNext) "X" else "O")
          val newHistory =  s.gameHistory.take(s.step)
          if (hasWinner(newSquare)) {
            State(newSquare, newHistory :+ newSquare, s.xIsNext, true, s.step)
          }else{
            State(newSquare, newHistory :+ newSquare, !s.xIsNext, false, s.step + 1)
          }
        }
      })

    def jumpTo(move: Int): Callback = {
      $.modState(s =>{
        val jumpToSquares = if (move == 0) State.initialSquares else s.gameHistory(move-1)
        State(
          jumpToSquares,
          //s.gameHistory.take(move),
          s.gameHistory,
          if (move % 2 == 0) true else false,
          hasWinner(jumpToSquares),
          move
        )
      }
      )
    }

    def renderMoves(history: Vector[Squares]): VdomArray ={
      def renderMove(move: Int): VdomElement =
        <.li(
          ^.key := move,
          <.a(
            ^.href := "#",
            ^.onClick --> jumpTo(move),
            if (move == 0) "Game Start" else "Move : " + move
      )
        )
      (0 to history.size).map(renderMove).toVdomArray
    }



    def render(p: Props, s: State): VdomElement =
      <.div(
        ^.cls := "game",
        <.div(
          ^.cls := "game-board",
          BoardComponent(s.currentSquares, handleClick)
        ),
        <.div(
          ^.cls := "game-info",
          <.div(
            ^.cls := "status",
            if(s.hasWinner){
              s"Winner: ${ if (s.xIsNext) "X" else "O"}"
            } else {
              s"Next Player: ${ if (s.xIsNext) "X" else "O"}"
            }
          ),
          <.ol(
            renderMoves(s.gameHistory)
          )
        )
      )
  }

  val Component = ScalaComponent.builder[Props]("GameComponent")
    .initialState(State.init)
    .renderBackend[Backend]
    .build

  def apply() = Component(Props())
}