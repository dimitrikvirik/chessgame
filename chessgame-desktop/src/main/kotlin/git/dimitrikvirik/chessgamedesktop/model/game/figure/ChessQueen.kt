package git.dimitrikvirik.chessgamedesktop.model.game.figure

class ChessQueen(
    chessFigureColor: ChessFigureColor,
     cord: Pair<Int, Int>
) : ChessFigure(
    ChessFigureType.QUEEN,
    chessFigureColor,
    cord
) {

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        val rook = ChessRook.getMovableBlocks(cord, this)
        val bishop = ChessBishop.getMovableBlocks(cord, this)
        val list = rook.toMutableList()
        list.addAll(bishop)
        return list
    }


}