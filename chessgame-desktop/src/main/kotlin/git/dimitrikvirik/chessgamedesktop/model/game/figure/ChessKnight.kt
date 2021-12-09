package git.dimitrikvirik.chessgamedesktop.model.game.figure

class ChessKnight(
    chessFigureColor: ChessFigureColor,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.KNIGHT,
    chessFigureColor,
    x, y
) {


    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {

        return ChessFigureUtil.Movable.knightAndKing(
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