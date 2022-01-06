package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.game.figure.ChessKing
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor

class ShahAction(coordination: Coordination, override val type: String = "SHAH") :
    SpecialAction(coordination, "Shah", type) {
    override fun run(figure: AbstractFigure) {
        val chessFigure = figure as ChessFigure
        val king = if (chessFigure.color == ChessFigureColor.BLACK) {
            chessFigure.chessGame.figureLayer.values.first {
                it is ChessKing && it.color == ChessFigureColor.WHITE
            }
        } else {
            chessFigure.chessGame.figureLayer.values.first {
                it is ChessKing && it.color == ChessFigureColor.BLACK
            }
        }
        chessFigure.chessGame.specialActionLayer[king.cord.pair] =
            ShahAction(Coordination(king.cord.pair, ObjectIndex.S_ACTION))

    }


}