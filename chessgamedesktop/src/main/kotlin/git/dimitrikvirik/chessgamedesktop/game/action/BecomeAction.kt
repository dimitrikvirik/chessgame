package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.model.AbstractAction
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.figure.*

class BecomeAction(coordination: Coordination, override val type: String) :
    AbstractAction(coordination, Coordination(0, 0, 0), "Become") {
    override fun run(figure: AbstractFigure) {
        val becomeType = type.split("-")[1]
        val pawn = (figure as ChessPawn)
        val becameFigure = when (becomeType) {
            "QUEEN" -> ChessQueen(pawn.cord, pawn.color)
            "ROOK" -> ChessRook(pawn.cord, pawn.color)
            "KNIGHT" -> ChessKnight(pawn.cord, pawn.color)
            "BISHOP" -> ChessBishop(pawn.cord, pawn.color)
            else -> {
                throw IllegalArgumentException("Unknown become pawn type")
            }
        }
        val figureLayer = pawn.figureLayer
        figureLayer.remove(pawn.cord.pair)
        figureLayer[pawn.cord.pair] = becameFigure
        becameFigure.checkShah()

    }

}