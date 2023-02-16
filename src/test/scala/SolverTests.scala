import Game.GameFigure
import Map.MapField
import Map.Models.{GameResult, WinGame}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import Map.Solver.Solver

import scala.util.{Failure, Success}

class SolverTests extends AnyFlatSpec with should.Matchers{
  val fileName = "src\\test\\files\\map.txt"
  val fileNameHard = "src\\test\\files\\solverMaps\\hardMap.txt"
  val fileNameImpossible = "src\\test\\files\\solverMaps\\mapImpossible.txt"

  "Map solver" should "correctly solve easy map " in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
       val solver = new Solver(map)
       val moveList = solver.findSolution(new GameFigure(map.getStartPosition))
        assert(moveList)
        assert(validateSolution(map, solver.solutionMoveList))
    }
  }

  it should "fail to solve map from mapImpossible.txt" in {
    try MapField(fileNameImpossible) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val solver = new Solver(map)
        val moveList = solver.findSolution(new GameFigure(map.getStartPosition))
        assert(!moveList)
    }
  }

  it should "solve map from hardMap.txt" in {
    try MapField(fileNameHard) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val solver = new Solver(map)
        val moveList = solver.findSolution(new GameFigure(map.getStartPosition))
        assert(moveList)
        assert(validateSolution(map, solver.solutionMoveList))
    }
  }
  def validateSolution(map: MapField, moveList: List[Char]): Boolean = {
    var gameFigure = new GameFigure(map.getStartPosition)

    for (symbol <- moveList)
      symbol match {
        case 'u' => gameFigure = gameFigure.up()
        case 'd' => gameFigure = gameFigure.down()
        case 'l' => gameFigure = gameFigure.left()
        case 'r' => gameFigure = gameFigure.right()
      }

    map.validateGameFigure(gameFigure)  match {
      case WinGame => true
      case _ => false
    }
  }
}
