package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import git.dimitrikvirik.chessgamedesktop.model.game.CellSpecialAction


class ChessKing(
    chessFigureColor: ChessFigureColor,
    cord: Pair<Int, Int>
) : ChessFigure(
    ChessFigureType.KING,
    chessFigureColor,
    cord
) {

    fun shah() {
        specialLayer[cord] = CellSpecialAction(cord, ActionType.SHAH)
    }

    fun swap(pair: Pair<Int, Int>) {
        actionLayer.clear()
        val rook = figureLayer[pair]!!
        figureLayer.remove(this.cord)
        figureLayer.remove(pair)
        if (pair.first == 1) {
            val toMoveRook = (pair.first + 3) to pair.second
            this.cord = (this.cord.first - 2) to this.cord.second
            this.hasFirstMove = true
            figureLayer[this.cord] = this
            rook.cord = toMoveRook
            rook.hasFirstMove = true
            figureLayer[toMoveRook] = rook
        } else {
            val toMoveRook = (pair.first - 2) to pair.second
            this.cord = (this.cord.first + 2) to this.cord.second
            this.hasFirstMove = true
            figureLayer[this.cord] = this
            rook.cord = toMoveRook
            rook.hasFirstMove = true
            figureLayer[toMoveRook] = rook
        }
    }

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val x = cord.first
        val y = cord.second

        return ChessFigureUtil.Movable.knightAndKing(
            this, listOf(
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