package git.dimitrikvirik.chessgamedesktop.model.game.figure

import javafx.application.Platform
import kotlin.math.abs

class ChessPawn(
    chessFigureColor: ChessFigureColor,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.PAWN,
    chessFigureColor,
    x,
    y
) {
    var onDoubleMove = false


    private fun become() {
        Platform.runLater {
            figureLayer.remove(x to y)
            figureLayer[x to y] = ChessQueen(color, x, y)
        }
    }


    override fun move(x: Int, y: Int) {
        onDoubleMove = !onDoubleMove && abs(y - this.y) == 2
        super.move(x, y)
        if ((color == ChessFigureColor.BLACK && y == 7) || (color == ChessFigureColor.WHITE && y == 0)) {
            become()
        }
    }


    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val list = if (!hasFirstMove) {
            if (color == ChessFigureColor.BLACK) {
                listOf(x to (y + 1), x to (y + 2))
            } else listOf(x to (y - 1), x to (y - 2))
        } else {
            if (color == ChessFigureColor.BLACK) {
                listOf(x to (y + 1))
            } else listOf(x to (y - 1))
        }

        val checkedList = mutableListOf<Pair<Int, Int>>()
        for (i in list.indices) {
            if (figureLayer[list[i]] != null) break
            checkedList.add(list[i])
        }

        return checkedList

    }

    override fun getAllKillableBlocks(): List<Pair<Int, Int>> {
        val list = if (color == ChessFigureColor.BLACK) {
            listOfNotNull(figureLayer[(x + 1) to (y + 1)], figureLayer[(x - 1) to (y + 1)])
        } else {
            listOfNotNull(figureLayer[(x - 1) to (y - 1)], figureLayer[(x + 1) to (y - 1)])
        }
        val filter = listOfNotNull(
            figureLayer[(x - 1) to y],
            figureLayer[(x + 1) to y]
        ).filterIsInstance<ChessPawn>().filter {
            it.onDoubleMove
        }

        return (list + filter).filter {
            it.color != this.color
        }.map { it.x to it.y }
    }

}