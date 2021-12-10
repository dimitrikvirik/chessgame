package git.dimitrikvirik.chessgamedesktop.model.game.figure

import javafx.application.Platform
import kotlin.math.abs

class ChessPawn(
    chessFigureColor: ChessFigureColor,
    cord: Pair<Int, Int>
) : ChessFigure(
    ChessFigureType.PAWN,
    chessFigureColor,
    cord
) {
    var onDoubleMove = false
    var hasFirstMove = false

    private fun become() {
        Platform.runLater {
            figureLayer.remove(cord)
            figureLayer[cord] = ChessQueen(color, cord)
        }
    }


    override fun move(cord: Pair<Int, Int>) {
        val y = cord.second

        onDoubleMove = !onDoubleMove && abs(y - this.cord.second) == 2
        super.move(cord)
        if ((color == ChessFigureColor.BLACK && y == 7) || (color == ChessFigureColor.WHITE && y == 0)) {
            become()
        }
        hasFirstMove = true
    }


    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val x = cord.first
        val y = cord.second

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
        val x = cord.first
        val y = cord.second

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
        }.map { it.cord.first to it.cord.second }
    }

}