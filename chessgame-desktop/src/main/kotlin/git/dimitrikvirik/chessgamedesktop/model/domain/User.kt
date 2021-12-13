package git.dimitrikvirik.chessgamedesktop.model.domain

import git.dimitrikvirik.chessgamedesktop.model.game.ChessGame

data class User(
    val username: String,
    val friends: ArrayList<User>? = null,
    val games: ArrayList<ChessGame>? = null
) {
}