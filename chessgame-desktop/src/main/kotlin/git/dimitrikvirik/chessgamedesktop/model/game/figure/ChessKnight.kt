package git.dimitrikvirik.chessgamedesktop.model.game.figure

class ChessKnight(
    chessFigureColor: ChessFigureColor,
    cord: Pair<Int, Int>
) : ChessFigure(
    ChessFigureType.KNIGHT,
    chessFigureColor,
    cord
) {

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val x = cord.first
        val y = cord.second
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