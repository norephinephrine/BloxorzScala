package Map.MapEditor

import Map.MapField

trait MapEditorOperation {
  def action(mapField: MapField): Unit
  def getOperationName : String
}

object InversionOperation extends MapEditorOperation {
  override def action(mapField: MapField): Unit = {
    val startPosition = mapField.getStartPosition
    val endPosition = mapField.getEndPosition

    // Set Start position
    mapField.setSymbolOnPosition('S', endPosition.row, endPosition.col)
    mapField.setSymbolOnPosition('T', startPosition.row, startPosition.col)
  }
  override def getOperationName: String = {
    "Inverse"
  }
}

object ReplacementOperation extends MapEditorOperation {
  override def action(mapField: MapField): Unit = {
    for (i <- 0 until mapField.n) {
      mapField.map(i) =
        mapField.map(i)
          .map(symbol => if(symbol == '.') 'o' else symbol)
    }
  }

  override def getOperationName: String = {
    "Replacement"
  }
}