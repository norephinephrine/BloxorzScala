package Map.Models

// Player move result
sealed trait GameResult
case object WinGame extends GameResult
case object ContinueGame extends GameResult
case object LoseGame extends GameResult
