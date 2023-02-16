package Map.Blocks

import Map.Models.MapBlock

object BlockBuilder {
  def createBlock(x:Int, y:Int, symbol: Char): MapBlock ={
    symbol match {
      case 'S' => new StartBlock(x,y)
      case 'T' => new EndBlock(x,y)
      case '-' => new BoundaryBlock(x,y)
      case '.' => new SpecialBlock(x,y)
      case 'o' => new BasicBlock(x,y)
    }
  }
}
