package Map.Blocks

import Map.Models.{GameResult, ContinueGame, LoseGame, MapBlock}

class SpecialBlock (x:Int, y:Int) extends MapBlock(x, y){
  override def validateBlockGameResult(isUpright: Boolean): GameResult =
  {
    if (isUpright)
      LoseGame
    else ContinueGame
  }

  override def symbolValue: Char = '.'
}
