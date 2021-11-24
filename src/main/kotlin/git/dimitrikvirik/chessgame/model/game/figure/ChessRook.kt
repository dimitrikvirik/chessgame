package git.dimitrikvirik.chessgame.model.game.figure

import git.dimitrikvirik.chessgame.model.game.*

class ChessRook(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.ROOK,
    chessFigureColor,
    board,
    x, y
) {

    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        TODO("Not yet implemented")
    }

    override fun getKillableBlocks(): List<Pair<Int, Int>> {
        TODO("Not yet implemented")
    }

    override fun kill(x: Int, y: Int): Boolean {
        TODO("Not yet implemented")
    }


}