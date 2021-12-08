package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.Action
import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import javafx.application.Platform


class ChessKing(
    chessFigureColor: ChessFigureColor,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.KING,
    chessFigureColor,
    x, y
) {

    fun shah() {

        Platform.runLater {
            actionLayer[this.x to this.y] = Action(ActionType.SHAH, this.x, this.y)
        }

    }

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
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