package Map.Models


abstract class MapBlock (val x:Int, val y: Int) {

  // validate Block value and return Game result
  def validateBlockGameResult(isUpright: Boolean) : GameResult

  // Value of symbol
  def symbolValue : Char
}
