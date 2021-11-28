package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard

class ChessKnight(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.KNIGHT,
    chessFigureColor,
    board,
    x, y
) {


    companion object {

    }

    override fun getMovableBlocks(): List<Pair<Int, Int>> {

        return getMovableBlocksForKnightKing(
            x, y, board, this, killableBlocks, listOf(
                (x + 1 to y - 2),
                (x + 2 to y - 1),
                (x + 2 to y + 1),
                (x + 1 to y + 2),
                (x - 1 to y - 2),
                (x - 2 to y - 1),
                (x - 2 to y + 1),
                (x - 1 to y + 2)
            )
        )

    }


}