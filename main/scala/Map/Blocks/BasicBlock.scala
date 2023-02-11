package Map.Blocks

import Map.Models.{GameResult, ContinueGame, MapBlock}

class BasicBlock (x:Int, y:Int) extends MapBlock(x, y){
  override def validateBlockGameResult(isUpright: Boolean): GameResult =
    {
      ContinueGame
    }

  override def symbolValue: Char = 'o'
}
