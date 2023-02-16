package Map.Blocks

import Map.Models.{GameResult, LoseGame, MapBlock}

class BoundaryBlock (x:Int, y:Int) extends MapBlock(x, y) {
  override def validateBlockGameResult(isUpright: Boolean): GameResult =
  {
    LoseGame
  }

  override def symbolValue: Char = '-'
}