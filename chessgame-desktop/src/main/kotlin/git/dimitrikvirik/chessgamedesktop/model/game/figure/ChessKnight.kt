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

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {

        return board.fixBlocks(ChessFigureUtil.Movable.knightAndKing(
            board, this, listOf(
                (x + 1 to y - 2),
                (x + 2 to y - 1),
                (x + 2 to y + 1),
                (x + 1 to y + 2),
                (x - 1 to y - 2),
                (x - 2 to y - 1),
                (x - 2 to y + 1),
                (x - 1 to y + 2)
            )
        ))

    }


}