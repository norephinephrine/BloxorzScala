package Game

// Position of figure

class GameFigure(var x1:Int, var y1:Int, var x2:Int, var y2:Int) {
  def this(row: Int,col: Int) = {
    this(col, row, col, row)
  }

  def this(gameFigure: GameFigure) = {
    this(gameFigure.x1, gameFigure.y1, gameFigure.x2, gameFigure.y2)
  }

  def getFigurePosition: PositionOfFigure =
  {
      if(y1 == y2 && x1 == x2) Upright
      else if (x1 == x2) Vertical
      else Horizontal
  }

  def left(): Unit = {
    getFigurePosition match {
      case Upright => changeStateX(-2,-1)
      case Horizontal => changeStateX(-1,-2)
      case Vertical => changeStateX(-1,-1)
    }
  }

  def right(): Unit = {
    getFigurePosition match {
      case Upright => changeStateX(1, 2)
      case Horizontal => changeStateX(2, 1)
      case Vertical => changeStateX(1, 1)
    }
  }

  def up(): Unit = {
    getFigurePosition match {
      case Upright => changeStateY(-1, -2)
      case Horizontal => changeStateY(-1, -1)
      case Vertical => changeStateY(-2, -1)
    }
  }

  def down(): Unit = {
    getFigurePosition match {
      case Upright => changeStateY(2, 1)
      case Horizontal => changeStateY(1, 1)
      case Vertical => changeStateY(1, 2)
    }
  }

  private def changeStateX(diff_x1: Int, diff_x2:Int): Unit = {
    x1 += diff_x1
    x2 += diff_x2
  }

  private def changeStateY(diff_y1: Int, diff_y2: Int): Unit = {
    y1 += diff_y1
    y2 += diff_y2
  }

  def printGameState(): String ={
    s"$x1-$y1-$x2-$y2"
  }
}
