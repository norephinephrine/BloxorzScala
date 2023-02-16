import Map.{InvalidMapException, InvalidSymbolException, MapField}

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._

import scala.util.{Failure, Success}

class MapLoadTests extends AnyFlatSpec with should.Matchers {
  val fileName = "src\\test\\files\\map.txt"
  val fileNameIncorrectSymbol = "src\\test\\files\\badMaps\\mapIncorrectSymbol.txt"
  val fileNameMissingStartPoint= "src\\test\\files\\badMaps\\mapMissingStartPoint.txt"


  "Map loader" should "correctly load map.txt" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(value) =>
    }
  }

  it should "fail to load map from mapIncorrectSymbol.txt " in {
    assertThrows[InvalidSymbolException] {
      try MapField(fileNameIncorrectSymbol) match {
        case Failure(exception) => throw exception
        case Success(value) =>
      }
    }
  }

  it should "fail to load map with missing start point" in {
    assertThrows[InvalidMapException] {
      try MapField(fileNameMissingStartPoint) match {
        case Failure(exception) => throw exception
        case Success(value) =>
      }
    }
  }
}