package git.dimitrikvirik.chessgamedesktop.model.domain

import git.dimitrikvirik.chessgamedesktop.model.game.ChessGame

data class User(
    val username: String,
    val rating: Double? = null,
    val friends: ArrayList<User>? = null,
    val gamesHistory: ArrayList<ChessGame>? = null
) {
}