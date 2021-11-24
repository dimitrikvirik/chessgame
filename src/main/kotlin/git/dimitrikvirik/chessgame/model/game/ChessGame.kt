package git.dimitrikvirik.chessgame.model.game


class ChessGame(
    val whitePlayer: ChessPlayer,
    val blackPlayer: ChessPlayer,
    val isPublic: Boolean,
    val invitedFrom:  Invitation,
    val chessBoard: ChessBoard
) {
    var winner: ChessPlayer? = null

}

enum class Invitation{
    PUBLIC,
    FRIEND,
    INVITE
}