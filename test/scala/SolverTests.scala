import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class Solver extends AnyFlatSpec with should.Matchers{
  val fileName = "src\\test\\files\\solverMaps\\map.txt"


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
