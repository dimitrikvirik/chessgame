package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard
import git.dimitrikvirik.chessgamedesktop.service.Action
import javafx.application.Platform


class ChessKing(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.KING,
    chessFigureColor,
    board,
    x, y
) {

    fun shah(){

        Platform.runLater{
            board.actionLayer[this.x to this.y] = Action.SHAH
            board.drawActionLayer()
        }

    }
    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        return getMovableBlocksForKnightKing(
            x, y, board, this, killableBlocks, listOf(
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