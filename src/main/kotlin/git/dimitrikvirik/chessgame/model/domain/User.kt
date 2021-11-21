package git.dimitrikvirik.chessgame.model.domain

import git.dimitrikvirik.chessgame.model.game.ChessGame

data class User(
    val username: String,
    val rating: Double? = null,
    val friends: ArrayList<User>? = null,
    val gamesHistory: ArrayList<ChessGame>? = null
) {
}