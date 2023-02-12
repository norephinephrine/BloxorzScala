package Game

import Map.Models.{ContinueGame, GameResult, LoseGame, WinGame}
import Map.{MapField, Position}

import scala.io.Source

class Game(mapField: MapField) {
    val startPosition: Position = mapField.getStartPosition
    private val gameFigure: GameFigure = new GameFigure(startPosition.row, startPosition.col)

    private def writeGameMeni(): Unit = {
        println()
        println("[w] Move block up")
        println("[s] Move block down")
        println("[a] Move block left")
        println("[d] Move block right")
        println("[read]Load moves from file")
        println()
        mapField.printMapWithFigure(gameFigure)
        println()
    }

    private def playMovesFromFile(fileName: String): Boolean =
    {
        val mapFile = Source.fromFile(fileName)
        val lines = mapFile.getLines()

        while(lines.hasNext)
        {
            val line: String = lines.next()
            line match {
                case "u" => gameFigure.up()
                case "d" => gameFigure.down()
                case "l" => gameFigure.left()
                case "r" => gameFigure.right()
            }
            Thread.sleep(250)
            val result = returnGameState()

            println(s"Played move $line")
            mapField.printMapWithFigure(gameFigure)
            result match {
                case WinGame =>
                    print(s"${Console.BLUE}You have won the game.${Console.RESET}")
                    return true
                case LoseGame =>
                    print(s"${Console.RED}You have lost the game.${Console.RESET}")
                    return true
                case _ =>
            }

        }
        mapFile.close
        false
    }

    def apply(): Unit = {
        while(true)
        {
            writeGameMeni()
            scala.io.StdIn.readLine() match {
                case "w" => gameFigure.up()
                case "s" => gameFigure.down()
                case "a" => gameFigure.left()
                case "d" => gameFigure.right()

                case "read" =>
                    println("Enter file name to read commands from")
                    val fileName = scala.io.StdIn.readLine()

                    try{
                        val isEnd = playMovesFromFile(fileName)
                        if(isEnd)return
                    }
                    catch
                    {
                        case  ex: Exception =>  print(s"${Console.RED}An exception occurred while reading moves from file $ex${Console.RESET}")
                    }

                case _ => println("A non valid command was used")
            }

            val result = returnGameState()

            result match {
                case WinGame =>
                    mapField.printMapWithFigure(gameFigure)
                    print(s"${Console.BLUE}You have won the game.${Console.RESET}")
                    return
                case LoseGame =>
                    mapField.printMapWithFigure(gameFigure)
                    print(s"${Console.RED}You have lost the game.${Console.RESET}")
                    return
                case _ =>
            }
        }
    }

    // Returns game State
    private def returnGameState(): GameResult = {
        gameFigure.getFigurePosition match {
            case Upright =>
                mapField.validatePosition(gameFigure.x1, gameFigure.y1, isUpright = true)
            case _ =>
                val result1 = mapField.validatePosition(gameFigure.x1, gameFigure.y1, isUpright = false)
                result1 match {
                    case WinGame => return WinGame
                    case LoseGame => return LoseGame
                    case _ =>
                }

                val result2 = mapField.validatePosition(gameFigure.x2, gameFigure.y2, isUpright = false)
                result2 match {
                    case WinGame => WinGame
                    case LoseGame => LoseGame
                    case _ => ContinueGame
                }
        }
    }
}
