package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard

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


    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val rook = board.fixBlocks(ChessRook.getMovableBlocks(x, y, board, this))
        val bishop = board.fixBlocks(ChessBishop.getMovableBlocks(x, y, board, this))
        val list = rook.toMutableList()
        list.addAll(bishop)
        return list
    }


}