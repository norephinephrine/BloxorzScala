package Map.MapEditor
import Map.MapField

class CompositeMapEditorOperation(operationName:String,actionList: List[MapField=>Unit]) extends MapEditorOperation {
  override def action(mapField: MapField): Unit = {
    for(action <- actionList)
      {
        action(mapField)
      }
  }

  override def getOperationName: String = {
    operationName
  }
}

