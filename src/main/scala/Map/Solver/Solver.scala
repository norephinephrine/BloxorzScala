package Map.Solver

import Game.GameFigure
import Map.MapField
import Map.Models.{LoseGame, WinGame}

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
import scala.collection.immutable.Queue

class Solver(map: MapField) {
  var solutionMoveList: List[Char] = List[Char]()

  def findSolution(figure: GameFigure): Boolean = {
    val gameFigure = new GameFigure(figure)
    val historySet = Set[String](gameFigure.printGameState())

    val gameState = new GameState(gameFigure,List[Char]())

    val queue = Queue[GameState](gameState)
    solutionMoveList = findSolutionTailrec(queue, historySet)

    if(solutionMoveList == Nil)
      false
    else true

  }

  // Solver no tailrec

  @tailrec
  private final def findSolutionTailrec(gameStateQueue: Queue[GameState], stateHistory: Set[String]): List[Char] = {
    if(gameStateQueue.isEmpty) return Nil

    gameStateQueue.dequeue match {
      case (gameState, queueLeftover) =>
        val gameFigure = gameState.gameFigure
        val moveList = gameState.moveList

        map.validateGameFigure (gameState.gameFigure) match {
          case WinGame =>
            gameState.moveList
          case LoseGame =>
            findSolutionTailrec(queueLeftover, stateHistory)
          case _ =>

            // Left move
            val(queueLeft,historyLeft) = createNewQueueAndHistory(
              gameStateQueue = queueLeftover,
              stateHistory = stateHistory,
              gameFigure = gameFigure.left(),
              moveList = moveList.concat ("l")
            )

              // Right move

            val (queueRight, historyRight) = createNewQueueAndHistory(
              gameStateQueue = queueLeft,
              stateHistory = historyLeft,
              gameFigure = gameFigure.right(),
              moveList = moveList.concat("r")
            )

              // Up move
            val (queueUp, historyUp) = createNewQueueAndHistory(
              gameStateQueue = queueRight,
              stateHistory = historyRight,
              gameFigure = gameFigure.up(),
              moveList = moveList.concat("u")
            )

              // Down move
            val (queueDown, historyDown) = createNewQueueAndHistory(
              gameStateQueue = queueUp,
              stateHistory = historyUp,
              gameFigure = gameFigure.down(),
              moveList = moveList.concat("d")
            )

            findSolutionTailrec(queueDown, historyDown)
      }
    }
}

  private def createNewQueueAndHistory(
   gameStateQueue: Queue[GameState],
   stateHistory: Set[String],
   gameFigure: GameFigure,
   moveList: List[Char]): (Queue[GameState], Set[String])= {
      if (!stateHistory.contains(gameFigure.printGameState())) {
        (gameStateQueue.enqueue(new GameState(gameFigure, moveList)),
        stateHistory + gameFigure.printGameState())
      }
      else (gameStateQueue, stateHistory)
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
