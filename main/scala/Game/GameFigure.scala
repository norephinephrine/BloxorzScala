package Game

// Position of figure

class GameFigure(row: Int,col: Int) {
  var x1 : Int =col
  var y1: Int =row

  var x2: Int = col
  var y2 : Int = row

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
}
