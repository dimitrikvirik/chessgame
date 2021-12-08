package git.dimitrikvirik.chessgamedesktop.model.game.figure

class ChessQueen(
    chessFigureColor: ChessFigureColor,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.QUEEN,
    chessFigureColor,
    x, y
) {

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val rook = ChessRook.getMovableBlocks(x, y, this)
        val bishop = ChessBishop.getMovableBlocks(x, y, this)
        val list = rook.toMutableList()
        list.addAll(bishop)
        return list
    }


}