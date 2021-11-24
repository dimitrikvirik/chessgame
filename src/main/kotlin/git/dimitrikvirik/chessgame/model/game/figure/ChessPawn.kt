package git.dimitrikvirik.chessgame.model.game.figure

import git.dimitrikvirik.chessgame.model.game.*

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
    var hasDoubleMove = false


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
        val list = if (color == ChessFigureColor.BLACK) ({
            val list = mutableListOf(board.figureLayer[(x + 1) to (y + 1)], board.figureLayer[(x - 1) to (y + 1)])
            list.addAll(
                listOfNotNull(
                    board.figureLayer[(x - 1) to y],
                    board.figureLayer[(x + 1) to y]
                ).filter { it.hasFirstMove })
            val filter = list.filterNotNull().filter { it.color != ChessFigureColor.BLACK }
            filter
        })
        else {
            {
                val list = mutableListOf(board.figureLayer[(x - 1) to (y - 1)], board.figureLayer[(x + 1) to (y - 1)])
                list.addAll(
                    listOfNotNull(
                        board.figureLayer[(x - 1) to y],
                        board.figureLayer[(x + 1) to y]
                    ).filter { it.hasFirstMove })

                val filter = list.filterNotNull().filter { it.color != ChessFigureColor.WHITE }
                filter
            }
        }
        return list().map {
            it.x to it.y
        }
    }

    override fun kill(x: Int, y: Int): Boolean {
        if(!hasFirstMove) hasFirstMove = true

        val chessFigure = board.figureLayer[x to y]
        if(chessFigure is ChessPawn && chessFigure.hasDoubleMove){
            this.x = x
            if(color == ChessFigureColor.BLACK) {
                this.x = x +  1
                this.y = y + 1
            }

        }
        this.x = x
        this.y = y

        board.removeFigure(this.x, this.y)
        board.clearActionLayer()
        board.addFigure(x, y, this)
        return true
    }
}