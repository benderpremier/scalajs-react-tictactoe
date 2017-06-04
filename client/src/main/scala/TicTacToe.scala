package example


import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import components.{GameComponent, HelloComponent}
import japgolly.scalajs.react.WebpackRequire

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

object TicTacToe extends js.JSApp{

  def require(): Unit = {
    WebpackRequire.React
    WebpackRequire.ReactDOM
    ()
  }

  @JSExport
  def main(): Unit = {
    // needed ton include the React and ReactDOM modules
    require()

    GameComponent()
      .renderIntoDOM(dom.document.getElementById("playground"))
  }
}
