import Map.MapEditor.{CompositeMapEditorOperation, InversionOperation, MapEditor, MapEditorInvalidOperationException, ReplacementOperation}
import Map.MapField
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.{Failure, Success}

class MapEditorOperationTests extends AnyFlatSpec with should.Matchers{
  val fileName = "src\\test\\files\\map.txt"
  val fileNameFilter = "src\\test\\files\\mapFilter.txt"

  "Map editor" should "correctly do inverse operation" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)

        val oldEndPosition = map.getEndPosition
        val oldStartPosition = map.getStartPosition
        InversionOperation.action(mapEditor.mapField)

        // Check if start and end block switched
        assert(map.map(oldEndPosition.row)(oldEndPosition.col) == 'S')
        assert(map.map(oldStartPosition.row)(oldStartPosition.col) == 'T')
    }
  }

  it should "correctly do replace operation" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)
        ReplacementOperation.action(mapEditor.mapField)

        // Check if fields are ordinary blocks now
        assert(map.map(1)(1) == 'o')
        assert(map.map(1)(4) == 'o')
        assert(map.map(3)(4) == 'o')
    }
  }

  it should "correctly do composite operation" in {
    try MapField(fileName) match {
      case Failure(exception) => throw exception
      case Success(map) =>
        val mapEditor = new MapEditor(map)

        val actionList =  List[MapField=> Unit](InversionOperation.action, ReplacementOperation.action)
        val operation = new CompositeMapEditorOperation("test", actionList)

        // Save old states
        val oldEndPosition = map.getEndPosition
        val oldStartPosition = map.getStartPosition

        operation.action(map)

        // Check if start and end block switched
        assert(map.map(oldEndPosition.row)(oldEndPosition.col) == 'S')
        assert(map.map(oldStartPosition.row)(oldStartPosition.col) == 'T')

        // Check if fields are ordinary blocks now
        assert(map.map(1)(1) == 'o')
        assert(map.map(1)(4) == 'o')
        assert(map.map(3)(4) == 'o')
    }
  }

}