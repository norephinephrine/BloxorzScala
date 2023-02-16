package Map.Blocks

import Map.Models.{GameResult, ContinueGame, MapBlock, WinGame}

class EndBlock (x:Int, y:Int) extends MapBlock(x, y) {
  override def validateBlockGameResult(isUpright: Boolean): GameResult =
  {
    if (isUpright)
      WinGame
    else ContinueGame
  }

  override def symbolValue: Char = 'T'
}