package Game

// Position of figure

class GameFigure(val x1:Int, val y1:Int, val x2:Int, val y2:Int) {
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

  def left(): GameFigure = {
    getFigurePosition match {
      case Upright => changeStateX(-2,-1)
      case Horizontal => changeStateX(-1,-2)
      case Vertical => changeStateX(-1,-1)
    }
  }

  def right(): GameFigure = {
    getFigurePosition match {
      case Upright => changeStateX(1, 2)
      case Horizontal => changeStateX(2, 1)
      case Vertical => changeStateX(1, 1)
    }
  }

  def up(): GameFigure = {
    getFigurePosition match {
      case Upright => changeStateY(-1, -2)
      case Horizontal => changeStateY(-1, -1)
      case Vertical => changeStateY(-2, -1)
    }
  }

  def down(): GameFigure = {
    getFigurePosition match {
      case Upright => changeStateY(2, 1)
      case Horizontal => changeStateY(1, 1)
      case Vertical => changeStateY(1, 2)
    }
  }

  private def changeStateX(diff_x1: Int, diff_x2:Int): GameFigure = {
    new GameFigure(x1+ diff_x1,y1,x2+diff_x2,y2)
  }

  private def changeStateY(diff_y1: Int, diff_y2: Int): GameFigure = {
    new GameFigure(x1,y1 + diff_y1,x2,y2 +diff_y2)
  }

  def printGameState(): String ={
    s"$x1-$y1-$x2-$y2"
  }
}
