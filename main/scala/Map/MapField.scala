package Map

import Game.GameFigure
import Map.Blocks.BlockBuilder
import Map.Models.{GameResult, LoseGame}

import scala.io.Source
import scala.util.Try
import Console.{RED, RESET, UNDERLINED, YELLOW_B}

class MapField (val n:Int, val m:Int, val map:Array[Array[Char]], val mapName:String){

  def printRed(symbol: Char) = {
    print(s"${RED}${symbol}${RESET}")
  }

  def printMapWithFigure(figure:GameFigure): Unit = {
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        if ((i == figure.y1 && figure.x1 == j)
          || ((i == figure.y2 && figure.x2 == j)))
          printRed(map(i)(j))
        else
          print(map(i)(j))
      }
      println()
    }
  }

  def getStartPosition(): Position ={
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        if(map(i)(j) == 'S')
          return new Position(i,j)
      }
    }

    throw new Exception("Couldn't find starting position")
  }

  def getEndPosition(): Position = {
    for (i <- 0 until n) {
      for (j <- 0 until m) {
        if (map(i)(j) == 'T')
          return new Position(i, j)
      }
    }

    throw new Exception("Couldn't find end position")
  }

  def validatePosition(x:Int, y:Int, isUpright: Boolean): GameResult = {
    if(x< 0 || y< 0 || x>=m || y>=n) return LoseGame

    BlockBuilder.createBlock(y, x, map(y)(x)).validateBlockGameResult(isUpright)
  }

}

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

  def checkFile(fileName: String): Unit =
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
          throw new Exception(s"The map has too many columns(${line.length}) in row ${i+1}. The expected column length is ${m}")
        }

        for (j <- 0 until m)
        {
          line.charAt(j) match
          {
            case 'S' =>
              {
              if(startExists)
              {
              throw new Exception("The map contains two starting positions which is invalid")
              }

              startExists = true;
              }

            case 'T' =>
            {
              if(endExists)
              {
                throw new Exception("The map contains two final positions which is invalid")
              }

              endExists = true;
            }

            case 'o' | '-' | '.' => None

            case x =>  throw new Exception(s"Invalid character read ${x}")
          }
        }
      }

      if (lines.hasNext)
      {
        throw new Exception(s"The file has too many rows but it should have ${n}")
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