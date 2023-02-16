package Game

sealed trait PositionOfFigure
case object Horizontal extends PositionOfFigure
case object Vertical extends PositionOfFigure
case object Upright extends PositionOfFigure

