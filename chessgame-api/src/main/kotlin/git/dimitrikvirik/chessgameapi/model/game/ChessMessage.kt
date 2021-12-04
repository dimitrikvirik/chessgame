package git.dimitrikvirik.chessgameapi.model.game

enum class ChessFigureColor {
    WHITE,
    BLACK
}

data class ChessMessage(
    var fromMove: Pair<Int, Int>,
    var toMove: Pair<Int, Int>,
    var playerColor: ChessFigureColor,
    var action: Action
)

