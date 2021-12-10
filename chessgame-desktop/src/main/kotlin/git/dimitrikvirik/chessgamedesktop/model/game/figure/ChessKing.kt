package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.Action
import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import git.dimitrikvirik.chessgamedesktop.model.game.Shah
import javafx.application.Platform


class ChessKing(
    chessFigureColor: ChessFigureColor,
    cord: Pair<Int, Int>
) : ChessFigure(
    ChessFigureType.KING,
    chessFigureColor,
    cord
) {

    fun shah() {
        shahLayer[cord] = Shah(cord)
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