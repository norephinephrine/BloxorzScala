import Game.Game
import Map.{MapField, MapsList}

import scala.util.{Failure, Success, Try}

object Main extends App {
  val mapList = new MapsList()

  def makeIncreaser(more: Int) = (x: Int) => x + more

  def writeMeni: Unit = {
    println()
    println("[1] Read map from file")
    println("[2] Start game")
    println("[3] Map configuration options")
    println()
  }

  def writeMeniGame: Unit = {
    println("[1] Read map from file")
    println("[2] Start game")
    println("[3] Map configuration options")
    println()
  }

  def writeMeniMap: Unit = {
    println("[1] Ukloni zadatu plocu")
    println("[2] Dodavanje ploce na zadatu poziciju")
    println()
  }

  while (true)
    {
      try{
        writeMeni

        scala.io.StdIn.readLine() match {
          case "1" => {
            println(s"Insert file name to read map from:")

            val fileName = scala.io.StdIn.readLine();

            try (MapField(fileName)) match {
              case Failure(exception) => println(s"Exception encountered while reading map from file ${fileName}: ${exception.getMessage}")
              case Success(value) => mapList.AddMap(value)
            }
          }
          case "2" => {
            if (mapList.IsEmpty) {
              throw new Exception("No map was read into the game")
            }

            println("Choose map to play:")
            mapList.PrintMapList

            val mapField = mapList.GetMapByMapIndex(scala.io.StdIn.readInt()-1)

            val game = new Game(mapField)
            game.apply()
          }
          case _ => println("A non valid command was used")
        }
      }
      catch {
        case e: Exception => print(s"${Console.RED}${e}${Console.RESET}"+e)
      }
    }

}
