package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor

class ChessKnight(
    coordination: Coordination,
    chessFigureColor: ChessFigureColor
) : ChessFigure(
    coordination,
    chessFigureColor.prefix + "Knight",
    chessFigureColor
) {

    override fun allMovableBlocks(): List<Pair<Int, Int>> {
        val x = cord.x
        val y = cord.y
        return FigureBuilder.Movable.knightAndKing(
            this, listOf(
                (x + 1 to y - 2),
                (x + 2 to y - 1),
                (x + 2 to y + 1),
                (x + 1 to y + 2),
                (x - 1 to y - 2),
                (x - 2 to y - 1),
                (x - 2 to y + 1),
                (x - 1 to y + 2)
            )
        )

    }


}