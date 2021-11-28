package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard


class ChessKing(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.KING,
    chessFigureColor,
    board,
    x, y
) {


    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        return getMovableBlocksForKnightKing(
            x, y, board, this, killableBlocks, listOf(
                (x - 1 to y - 1),
                (x to y - 1),
                (x + 1 to y - 1),
                (x + 1 to y),
                (x + 1 to y + 1),
                (x to y + 1),
                (x - 1 to y + 1),
                (x - 1 to y)
            )
        )
    }


}