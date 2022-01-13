package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.action.BecomeAction
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor
import kotlin.math.abs

class ChessPawn(
    coordination: Coordination,
    chessFigureColor: ChessFigureColor
) : ChessFigure(
    coordination,
    chessFigureColor.prefix + "Pawn",
    chessFigureColor
) {


    var onDoubleMove = false


    private fun become() {
        if (chessGame.joinedChessPlayer.color == color)
            chessGame.send(BecomeAction(this.cord, "BECOME-QUEEN"))
    }


    override fun move(coordination: Pair<Int, Int>) {
        val y = coordination.second

        onDoubleMove = !onDoubleMove && abs(y - this.cord.y) == 2
        super.move(coordination)
        if ((color == ChessFigureColor.BLACK && y == 7) || (color == ChessFigureColor.WHITE && y == 0)) {
            become()
        }
        hasFirstMove = true
    }


    override fun kill(coordination: Pair<Int, Int>) {

        val chessFigure = figureLayer[coordination]!!
        figureLayer.remove(coordination)
        val pair = if (chessFigure is ChessPawn && chessFigure.onDoubleMove && this.cord.y == coordination.second) {
            if (color == ChessFigureColor.BLACK) {
                (coordination.first to coordination.second + 1)

            } else {
                (coordination.first to coordination.second - 1)
            }

        } else coordination

        move(pair)

    }

    override fun allMovableBlocks(): MutableList<Pair<Int, Int>> {
        val x = cord.x
        val y = cord.y

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

    override fun allKillableBlocks(): List<Pair<Int, Int>> {
        val x = cord.x
        val y = cord.y

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
        }.filter {
            if (color == ChessFigureColor.BLACK) {
                figureLayer[it.cord.x to (it.cord.y + 1)] == null
            } else {
                figureLayer[it.cord.x to (it.cord.y - 1)] == null

            }
        }

        val finalList = list + filter
        return finalList.filterIsInstance<ChessFigure>().filter {
            it.color != this.color
        }.map { it.cord.x to it.cord.y }
    }

}