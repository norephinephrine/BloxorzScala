package Game

import Map.Models.{ContinueGame, GameResult, LoseGame, WinGame}
import Map.{MapField, Position}

import scala.io.Source
import scala.util.{Failure, Success, Try}

class Game(mapField: MapField) {
    val startPosition = mapField.getStartPosition
    val gameFigure = new GameFigure(startPosition.x, startPosition.y)

    def writeGameMeni: Unit = {
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

    def playMovesFromFile(fileName: String): Boolean =
    {
        val mapFile = Source.fromFile(fileName)
        val lines = mapFile.getLines()

        while(lines.hasNext)
        {
            val line = lines.next() match {
                case "u" => gameFigure.up
                case "d" => gameFigure.down
                case "l" => gameFigure.left
                case "r" => gameFigure.right
            }
            Thread.sleep(250)
            val result = returnGameState()

            println(s"Played move ${line}")
            mapField.printMapWithFigure(gameFigure)
            result match {
                case WinGame => {
                    print(s"${Console.BLUE}You have won the game.${Console.RESET}")
                    return true
                }
                case LoseGame => {
                    print(s"${Console.RED}You have lost the game.${Console.RESET}")
                    return true
                }
                case _ =>
            }

        }
        mapFile.close
        false
    }

    def apply(){
        while(true)
        {
            writeGameMeni
            scala.io.StdIn.readLine() match {
                case "w" => gameFigure.up
                case "s" => gameFigure.down
                case "a" => gameFigure.left
                case "d" => gameFigure.right

                case "read" => {
                    println("Enter file name to read commands from")
                    val fileName = scala.io.StdIn.readLine();

                    try{
                        val isEnd = playMovesFromFile(fileName)
                        if(isEnd)return None
                    }
                    catch
                    {
                        case  ex: Exception =>  print(s"${Console.RED}An exception occured while reading moves from file ${ex}${Console.RESET}")
                    }

                }

                case _ => println("A non valid command was used")
            }

            val result = returnGameState()

            result match {
                case WinGame =>{
                    mapField.printMapWithFigure(gameFigure)
                    print(s"${Console.BLUE}You have won the game.${Console.RESET}")
                    return None
                }
                case LoseGame => {
                    mapField.printMapWithFigure(gameFigure)
                    print(s"${Console.RED}You have lost the game.${Console.RESET}")
                    return None
                }
                case _ =>
            }
        }
    }

    // Returns game State
    def returnGameState(): GameResult = {
        gameFigure.getFigurePosition() match {
            case Upright => {
                mapField.validatePosition(gameFigure.x1, gameFigure.y1, true)
            }
            case _ => {
                val result1 = mapField.validatePosition(gameFigure.x1, gameFigure.y1, false)
                result1 match {
                    case WinGame => return WinGame
                    case LoseGame => return LoseGame
                    case _ => ContinueGame
                }

                val result2 = mapField.validatePosition(gameFigure.x2, gameFigure.y2, false)
                result2 match {
                    case WinGame => WinGame
                    case LoseGame => LoseGame
                    case _ => ContinueGame
                }
            }
        }
    }
}
