package git.dimitrikvirik.chessgame.model.game

import git.dimitrikvirik.chessgame.model.domain.User

data class ChessPlayer(
    val chessFigureColor: ChessFigureColor,
    val user: User
)