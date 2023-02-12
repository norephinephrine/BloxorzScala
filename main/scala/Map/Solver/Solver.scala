package Map.Solver

import Game.GameFigure
import Map.MapField
import Map.Models.{LoseGame, WinGame}

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
import scala.collection.immutable.{HashMap, Queue}

class Solver(map: MapField) {
  var solutionMoveList: List[Char] = List[Char]()

  def findSolution(figure: GameFigure): Boolean = {
    val gameFigure = new GameFigure(figure)
    val historySet = Set[String](gameFigure.printGameState())

    val gameState = new GameState(gameFigure,List[Char]())

    val queue = Queue[GameState](gameState)
    findSolutionTailrec(queue, historySet)
  }

  // Solver no tailrec

  @tailrec
  final def findSolutionTailrec(gameStateQueue: Queue[GameState], stateHistory: Set[String]): Boolean = {
    if(gameStateQueue.isEmpty) return false

    gameStateQueue.dequeue match {
      case (gameState, queueLeft) =>
        val gameFigure = gameState.gameFigure
        val moveList = gameState.moveList

        map.validateGameFigure (gameState.gameFigure) match {
          case WinGame =>
            solutionMoveList = gameState.moveList
            true
          case LoseGame =>
            findSolutionTailrec(queueLeft, stateHistory)
          case _ =>
            var stateHistoryNew = stateHistory
            var queueNew = queueLeft

            // Left move
            val gameFigureLeft = new GameFigure (gameFigure)
            gameFigureLeft.left ()
            val moveListLeft = moveList.concat ("l")

            if(!stateHistory.contains(gameFigureLeft.printGameState())){
              queueNew = queueNew.enqueue(new GameState(gameFigureLeft,moveListLeft))
              stateHistoryNew += gameFigureLeft.printGameState()
            }

              // Right move
            val gameFigureRight = new GameFigure (gameFigure)
            gameFigureRight.right ()
            val moveListRight = moveList.concat ("r")

            if (!stateHistory.contains(gameFigureRight.printGameState())) {
              queueNew = queueNew.enqueue(new GameState(gameFigureRight, moveListRight))
              stateHistoryNew += gameFigureRight.printGameState()
            }

              // Up move
            val gameFigureUp = new GameFigure (gameFigure)
            gameFigureUp.up ()
            val moveListUp = moveList.concat ("u")

            if (!stateHistory.contains(gameFigureUp.printGameState())) {
              queueNew = queueNew.enqueue(new GameState(gameFigureUp, moveListUp))
              stateHistoryNew += gameFigureUp.printGameState()
            }

              // Down move
            val gameFigureDown = new GameFigure (gameFigure)
            gameFigureDown.down ()
            val moveListDown = moveList.concat ("d")

            if (!stateHistory.contains(gameFigureDown.printGameState())) {
              queueNew = queueNew.enqueue(new GameState(gameFigureDown, moveListDown))
              stateHistoryNew += gameFigureDown.printGameState()
            }

            findSolutionTailrec(queueNew, stateHistoryNew)
      }
    }
}

  def printSolution(): Unit = {
    println("Solution:")
    for (move <- solutionMoveList) {
      print(s"$move ")
    }
  }

  def writeToFileSolution(): Unit = {
    val printWriter = new PrintWriter(new File("solution.txt"))

    for(move <- solutionMoveList){
      printWriter.println(move)
    }
    printWriter.close()
  }
}
