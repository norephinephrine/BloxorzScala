package Map

class MapsList {
  var mapsList: List[MapField] = List()

  def AddMap(map: MapField): Unit = {
    mapsList = mapsList.appended(map)
  }

  def PrintMapList(): Unit = {
    var index = 1 : Int;
    for (map <- mapsList) {
      println(s"${index}. ${map.mapName}")
      index +=1
    }
  }

  def GetMapByMapName(mapName: String): MapField = {
    for (map <- mapsList)
      if(map.mapName == mapName)
        return map

    throw new Exception("Map was not found in loaded maps")
  }

  def GetMapByMapIndex(index: Int): MapField = {
      mapsList(index)
  }

  def IsEmpty(): Boolean =
  {
    mapsList.isEmpty
  }

}
