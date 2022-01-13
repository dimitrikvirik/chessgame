package git.dimitrikvirik.chessgamedesktop.core.model

import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor

data class ChessPlayer(
    val userId: String,
    val color: ChessFigureColor
)