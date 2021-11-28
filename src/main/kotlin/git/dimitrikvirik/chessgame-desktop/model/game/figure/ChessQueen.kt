package git.dimitrikvirik.chessgame.model.game.figure

import git.dimitrikvirik.chessgame.model.game.*

class ChessQueen(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.QUEEN,
    chessFigureColor,
    board,
    x, y
) {


    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        val rook = ChessRook.getMovableBlocks(x, y, board, this, killableBlocks)
        val bishop = ChessBishop.getMovableBlocks(x, y, board, this, killableBlocks)
        val list = rook.toMutableList()
        list.addAll(bishop)
        return list
    }


}