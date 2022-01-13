package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor

class ChessQueen(
    coordination: Coordination,
    chessFigureColor: ChessFigureColor
) : ChessFigure(
    coordination,
    chessFigureColor.prefix + "Queen",
    chessFigureColor
) {

    override fun allMovableBlocks(): List<Pair<Int, Int>> {
        val rook = ChessRook.getMovableBlocks(cord.x to cord.y, this)
        val bishop = ChessBishop.getMovableBlocks(cord.x to cord.y, this)
        val list = rook.toMutableList()
        list.addAll(bishop)
        return list
    }


}