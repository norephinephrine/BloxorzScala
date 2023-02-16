import Game.GameFigure
import Map.MapEditor.{CompositeMapEditorOperation, InversionOperation, MapEditor, MapEditorInvalidOperationException, ReplacementOperation}
import Map.MapField
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.{Failure, Success}

class GameFigureMoveTests extends AnyFlatSpec with should.Matchers{
  "Game figure when moved left" should "do this correctly while upright" in {
    val gameFigure = new GameFigure(2,2)
    val newGameFigure = gameFigure.left()

    assert(newGameFigure.x1 == 0)
    assert(newGameFigure.y1 == 2)
    assert(newGameFigure.x2 == 1)
    assert(newGameFigure.y2 == 2)
  }

  it should "do this correctly while horizontal" in {
    val gameFigure = new GameFigure(1,2,2,2)
    val newGameFigure = gameFigure.left()

    assert(newGameFigure.x1 == 0)
    assert(newGameFigure.y1 == 2)
    assert(newGameFigure.x2 == 0)
    assert(newGameFigure.y2 == 2)
  }

  it should "do this correctly while vertical" in {
    val gameFigure = new GameFigure(2, 2, 2, 1)
    val newGameFigure = gameFigure.left()

    assert(newGameFigure.x1 == 1)
    assert(newGameFigure.y1 == 2)
    assert(newGameFigure.x2 == 1)
    assert(newGameFigure.y2 == 1)
  }

  "Game figure when moved right" should "do this correctly while upright" in {
    val gameFigure = new GameFigure(2, 2)
    val newGameFigure = gameFigure.right()

    assert(newGameFigure.x1 == 3)
    assert(newGameFigure.y1 == 2)
    assert(newGameFigure.x2 == 4)
    assert(newGameFigure.y2 == 2)
  }

  it should "do this correctly while horizontal" in {
    val gameFigure = new GameFigure(1, 2, 2, 2)
    val newGameFigure = gameFigure.right()

    assert(newGameFigure.x1 == 3)
    assert(newGameFigure.y1 == 2)
    assert(newGameFigure.x2 == 3)
    assert(newGameFigure.y2 == 2)
  }

  it should "do this correctly while vertical" in {
    val gameFigure = new GameFigure(2, 2, 2, 1)
    val newGameFigure = gameFigure.right()

    assert(newGameFigure.x1 == 3)
    assert(newGameFigure.y1 == 2)
    assert(newGameFigure.x2 == 3)
    assert(newGameFigure.y2 == 1)
  }

  "Game figure when moved up" should "do this correctly while upright" in {
    val gameFigure = new GameFigure(2, 2)
    val newGameFigure = gameFigure.up()

    assert(newGameFigure.x1 == 2)
    assert(newGameFigure.y1 == 1)
    assert(newGameFigure.x2 == 2)
    assert(newGameFigure.y2 == 0)
  }

  it should "do this correctly while horizontal" in {
    val gameFigure = new GameFigure(1, 2, 2, 2)
    val newGameFigure = gameFigure.up()

    assert(newGameFigure.x1 == 1)
    assert(newGameFigure.y1 == 1)
    assert(newGameFigure.x2 == 2)
    assert(newGameFigure.y2 == 1)
  }

  it should "do this correctly while vertical" in {
    val gameFigure = new GameFigure(2, 2, 2, 1)
    val newGameFigure = gameFigure.up()

    assert(newGameFigure.x1 == 2)
    assert(newGameFigure.y1 == 0)
    assert(newGameFigure.x2 == 2)
    assert(newGameFigure.y2 == 0)
  }

  "Game figure when moved down" should "do this correctly while upright" in {
    val gameFigure = new GameFigure(2, 2)
    val newGameFigure = gameFigure.down()

    assert(newGameFigure.x1 == 2)
    assert(newGameFigure.y1 == 4)
    assert(newGameFigure.x2 == 2)
    assert(newGameFigure.y2 == 3)
  }

  it should "do this correctly while horizontal" in {
    val gameFigure = new GameFigure(1, 2, 2, 2)
    val newGameFigure = gameFigure.down()

    assert(newGameFigure.x1 == 1)
    assert(newGameFigure.y1 == 3)
    assert(newGameFigure.x2 == 2)
    assert(newGameFigure.y2 == 3)
  }

  it should "do this correctly while vertical" in {
    val gameFigure = new GameFigure(2, 2, 2, 1)
    val newGameFigure = gameFigure.down()

    assert(newGameFigure.x1 == 2)
    assert(newGameFigure.y1 == 3)
    assert(newGameFigure.x2 == 2)
    assert(newGameFigure.y2 == 3)
  }

}