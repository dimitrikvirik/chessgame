package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard
import kotlin.math.abs

class ChessPawn(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.PAWN,
    chessFigureColor,
    board,
    x,
    y
) {
    var onDoubleMove = false


    override fun move(x: Int, y: Int) {
        onDoubleMove = !onDoubleMove && abs(y - this.y) == 2
        super.move(x, y)
        if ((color == ChessFigureColor.BLACK && y == 7) || (color == ChessFigureColor.WHITE && y == 0)) {
            become()
        }
    }

    fun become() {
        board.removeFigure(x, y)
        board.addFigure(x, y, ChessQueen(color, board, x, y))
    }

    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        val list = if (!hasFirstMove) {
            if (color == ChessFigureColor.BLACK) {
                listOf(x to (y + 1), x to (y + 2))
            } else listOf(x to (y - 1), x to (y - 2))
        } else {
            if (color == ChessFigureColor.BLACK) {
                listOf(x to (y + 1))
            } else listOf(x to (y - 1))
        }
        return list.filter {
            board.figureLayer[it] == null && it.first in 0..7 && it.second in 0..7
        }
    }

    override fun getKillableBlocks(): List<Pair<Int, Int>> {
        val list = if (color == ChessFigureColor.BLACK) {
            listOfNotNull(board.figureLayer[(x + 1) to (y + 1)], board.figureLayer[(x - 1) to (y + 1)])
        } else {
            listOfNotNull(board.figureLayer[(x - 1) to (y - 1)], board.figureLayer[(x + 1) to (y - 1)])
        }
        val filter = listOfNotNull(
            board.figureLayer[(x - 1) to y],
            board.figureLayer[(x + 1) to y]
        ).filterIsInstance<ChessPawn>().filter {
            it.onDoubleMove
        }

        return (list + filter).filter {
            it.color != this.color
        }.map { it.x to it.y }
    }

}