package Map

import Game.{GameFigure, Upright}
import Map.Blocks.BlockBuilder
import Map.Models.{ContinueGame, GameResult, LoseGame, WinGame}

import scala.io.Source
import scala.util.Try
import Console.{RED, RESET}

class MapField (val n:Int, val m:Int, val map:Array[Array[Char]], val mapName:String) {

  def makeCopy(copyName: String):MapField = {
    val newMap = makeMapCopy
    new MapField(n, m, newMap, copyName)
  }

  def makeMapCopy: Array[Array[Char]] = {
    val newMap = new Array[Array[Char]](n)

    for (i <- 0 until n) {
      newMap(i) = new Array[Char](m)
      for (j <- 0 until m) {
        newMap(i)(j) = map(i)(j)
      }
    }
    newMap
  }
  private def printRed(symbol: Char): Unit = {
    print(s"$RED$symbol$RESET")
  }

  def printMapWithFigure(figure:GameFigure): Unit = {
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        if ((i == figure.y1 && figure.x1 == j)
          || i == figure.y2 && figure.x2 == j)
          printRed(map(i)(j))
        else
          print(map(i)(j))
      }
      println()
    }
  }

  def printMap(): Unit = {
    for (i <- 0 until n) {
      for (j <- 0 until m) {
          print(map(i)(j))
      }
      println()
    }
  }

  def getStartPosition: Position ={
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        if(map(i)(j) == 'S')
          return new Position(i,j)
      }
    }

    throw new Exception("Couldn't find starting position")
  }

  def getEndPosition: Position = {
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        if (map(i)(j) == 'T')
          return new Position(i,j)
      }
    }

    throw new Exception("Couldn't find end position")
  }

  def setSymbolOnPosition(symbol: Char, row: Int, col: Int): Unit = {
    map(row)(col) = symbol
  }


  def validateGameFigure(gameFigure: GameFigure): GameResult = {
    gameFigure.getFigurePosition match {
      case Upright =>
        validatePosition(gameFigure.y1, gameFigure.x1, isUpright = true)
      case _ =>
        val result1 = validatePosition(gameFigure.y1, gameFigure.x1, isUpright = false)
        result1 match {
          case WinGame => return WinGame
          case LoseGame => return LoseGame
          case _ =>
        }

        val result2 = validatePosition(gameFigure.y2, gameFigure.x2, isUpright = false)
        result2 match {
          case WinGame => WinGame
          case LoseGame => LoseGame
          case _ => ContinueGame
        }
    }
  }
  def validatePosition(row:Int, col:Int, isUpright: Boolean): GameResult = {
    if(row< 0 || col< 0 || row>=n || col>=m) return LoseGame

    BlockBuilder.createBlock(row, row, map(row)(col))
      .validateBlockGameResult(isUpright)
  }

}

// Object
object MapField
{
  def apply(fileName: String): Try[MapField] =
  {
    Try {
      val mapFile = Source.fromFile(fileName)
      val lines = mapFile.getLines()

      val n = lines.next().toInt
      val m = lines.next().toInt
      val map: Array[Array[Char]] = new Array[Array[Char]](n)

      for (i <- 0 until n) {
        val line = lines.next()
        map(i) = new Array[Char](m)
        for (j <- 0 until m) {
          map(i)(j) = line.charAt(j)
        }
      }
      mapFile.close

      checkFile(fileName)
      new MapField(n, m, map, fileName.dropRight(4))
    }
  } : Try[MapField]

  private def checkFile(fileName: String): Unit =
  {
    val mapFile = Source.fromFile(fileName)

    try
    {
      val lines =  mapFile.getLines()
      val n = lines.next().toInt
      val m = lines.next().toInt

      var startExists:Boolean = false
      var endExists:Boolean = false

      for (i <- 0 until n) {
        val line = lines.next()
        if (line.length != m)
        {
          throw new Exception(s"The map has too many columns(${line.length}) in row ${i+1}. The expected column length is $m")
        }

        for (j <- 0 until m)
        {
          line.charAt(j) match
          {
            case 'S' =>
              if(startExists)
              {
              throw new Exception("The map contains two starting positions which is invalid")
              }
              startExists = true

            case 'T' =>
              if(endExists)
              {
                throw new Exception("The map contains two final positions which is invalid")
              }

              endExists = true;

            case 'o' | '-' | '.' =>

            case x =>  throw new Exception(s"Invalid character read $x")
          }
        }
      }

      if (lines.hasNext)
      {
        throw new Exception(s"The file has too many rows but it should have $n")
      }

      if (!startExists)
      {
        throw new Exception("The map doesn't contain a starting position")
      }

      if (!endExists)
      {
        throw new Exception("The map doesn't contain a final position")
      }
    }
    finally
    {
      mapFile.close
    }
  }
}