import Game.Game
import Map.MapEditor.MapEditor
import Map.{MapField, MapsList}

import scala.util.{Failure, Success}

object Main extends App {
  private val mapList = new MapsList()

  private def writeMeni(): Unit = {
    println()
    println("[1] Read map from file")
    println("[2] Start game")
    println("[3] Map editor")
    println()
  }

  while (true)
    {
      try{
        writeMeni()

        scala.io.StdIn.readLine() match {
          case "1" =>
            println(s"Insert file name to read map from:")

            val fileName = scala.io.StdIn.readLine()

            try MapField(fileName) match {
              case Failure(exception) => println(s"Exception encountered while reading map from file $fileName: ${exception.getMessage}")
              case Success(value) => mapList.AddMap(value)
            }
          case "2" =>
            if (mapList.IsEmpty()) {
              throw new Exception("No map was read into the game")
            }

            println("Choose map to play:")
            mapList.PrintMapList()

            val mapField = mapList.GetMapByMapIndex(scala.io.StdIn.readInt()-1)

            val game = new Game(mapField)
            game.apply()

          case "3" =>
            if (mapList.IsEmpty()) {
              throw new Exception("No map was read into the game")
            }

            println("Choose map to edit :")
            mapList.PrintMapList()

            val mapField = mapList.GetMapByMapIndex(scala.io.StdIn.readInt() - 1)

            println("Choose new name of map:")
            val mapName = scala.io.StdIn.readLine()


            val mapEditor = new MapEditor(mapField.makeCopy(mapName))
            val changedMap = mapEditor.startEditor
            mapList.AddMap(changedMap)

          case _ => println("A non valid command was used")
        }
      }
      catch {
        case e: Exception => print(s"${Console.RED}$e${Console.RESET}")
      }
    }

}
