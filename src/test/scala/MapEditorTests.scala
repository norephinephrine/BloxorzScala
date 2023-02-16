import Map.MapEditor.{MapEditor, MapEditorInvalidOperationException}
import Map.MapField
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.{Failure, Success}

class MapEditorTests extends AnyFlatSpec with should.Matchers{
  val fileName = "src\\test\\files\\map.txt"
  val fileNameFilter = "src\\test\\files\\mapFilter.txt"

  "Map editor" should "correctly set block on boundary field" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        mapEditor.setBlockOnBoundary(0,0)
        assert(map.map(0)(0) == 'o')
    }
  }

  it should "fail to set block on non-bondary field" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        assertThrows[MapEditorInvalidOperationException] {
          mapEditor.setBlockOnBoundary(1, 1)
        }
    }
  }

  it should "correctly remove ordinary block" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        mapEditor.removeBlockFromBoundary(1, 1)
        assert(map.map(1)(1) == '-')
    }
  }

  it should "fail to remove non-ordinary block and change it to empty" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        assertThrows[MapEditorInvalidOperationException] {
          mapEditor.removeBlockFromBoundary(0, 1)
        }
    }
  }


  it should "correctly change special block to ordinary one" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        mapEditor.changeSpecialToBasicBlock(1,4)
        assert(map.map(1)(4) == 'o')
    }
  }

  it should "fail to change non-special block to ordinary one" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        assertThrows[MapEditorInvalidOperationException] {
          mapEditor.changeSpecialToBasicBlock(1, 1)
        }
    }
  }

  it should "correctly change ordinary one to special" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        mapEditor.changeBasicBlockToSpecial(1, 1)
        assert(map.map(1)(1) == '.')
    }
  }

  it should "fail to change non-ordinary block to special one" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        assertThrows[MapEditorInvalidOperationException] {
          mapEditor.changeBasicBlockToSpecial(1, 4)
        }
    }
  }

  it should "correctly set starting block" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        val oldPosition = map.getStartPosition
        mapEditor.setStartingBlock(1,1)
        assert(map.map(oldPosition.row)(oldPosition.col) == 'o')
        assert(map.map(1)(1) == 'S')
    }
  }

  it should "correctly set end block" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        val oldPosition = map.getEndPosition
        mapEditor.setEndBlock(1, 2)
        assert(map.map(oldPosition.row)(oldPosition.col) == 'o')
        assert(map.map(1)(2) == 'T')
    }
  }

  it should "correctly filter special blocks out" in {
    try MapField(fileNameFilter) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)

        mapEditor.doFilter(2)
        assert(map.map(1)(4) == 'o')
        assert(map.map(1)(1) == '.')
    }
  }

  it should "not be able to filter special blocks out because it is not in range" in {
    try MapField(fileNameFilter) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)

        mapEditor.doFilter(1)
        assert(map.map(1)(4) == '.')
        assert(map.map(1)(1) == '.')
    }
  }

}
