package git.dimitrikvirik.chessgame.model.game

import git.dimitrikvirik.chessgame.model.game.figure.*

interface ChessFigureMove {
    fun getMovableBlocks(): List<Pair<Int, Int>>
    fun getKillableBlocks(): List<Pair<Int, Int>>
    fun move(x: Int, y: Int): Boolean
    fun kill(x: Int, y: Int): Boolean

}

enum class ChessFigureType(private val black: String, private val white: String) {
    PAWN("b_pawn_1x.png", "w_pawn_1x.png"),
    KNIGHT("b_knight_1x.png", "w_knight_1x.png"),
    BISHOP("b_bishop_1x.png", "w_bishop_1x.png"),
    ROOK("b_rook_1x.png", "w_rook_1x.png"),
    QUEEN("b_queen_1x.png", "w_queen_1x.png"),
    KING("b_king_1x.png", "w_king_1x.png");

    fun getByColor(chessFigureColor: ChessFigureColor): String {
        return if (chessFigureColor == ChessFigureColor.BLACK) {
            this.black
        } else this.white
    }

}


enum class ChessFigureColor {
    WHITE,
    BLACK
}

abstract class ChessFigure(
    val chessFigureType: ChessFigureType,
    open val color: ChessFigureColor,
    open val board: ChessBoard,
    open var x: Int,
    open var y: Int
) : ChessFigureMove {
    val isAlive: Boolean = true
    var hasFirstMove: Boolean = false

    override fun move(x: Int, y: Int): Boolean {
        if(this is ChessPawn && y - this.y == 2){
            this.hasDoubleMove = true
        }

        if(!hasFirstMove) hasFirstMove = true
        board.removeFigure(this.x, this.y)
        board.clearActionLayer()
        this.x = x
        this.y = y
        board.addFigure(x, y, this)
        return true
    }



    companion object {
        fun getByNumber(x: Int, y: Int, board: ChessBoard): ChessFigure? {
            return when (x to y) {
                7 to 0, 0 to 0 -> ChessRook(ChessFigureColor.BLACK, board, x, y)
                1 to 0, 6 to 0 -> ChessKnight(ChessFigureColor.BLACK, board, x, y)
                2 to 0, 5 to 0 -> ChessBishop(ChessFigureColor.BLACK, board, x, y)
                3 to 0 -> ChessQueen(ChessFigureColor.BLACK, board, x, y)
                4 to 0 -> ChessKing(ChessFigureColor.BLACK, board, x, y)
                0 to 1, 1 to 1, 2 to 1, 3 to 1, 4 to 1, 5 to 1, 6 to 1, 7 to 1 -> ChessPawn(
                    ChessFigureColor.BLACK,
                    board,
                    x, y
                )
                7 to 7, 0 to 7 -> ChessRook(ChessFigureColor.WHITE, board, x, y)
                1 to 7, 6 to 7 -> ChessKnight(ChessFigureColor.WHITE, board, x, y)
                2 to 7, 5 to 7 -> ChessBishop(ChessFigureColor.WHITE, board, x, y)
                3 to 7 -> ChessQueen(ChessFigureColor.WHITE, board, x, y)
                4 to 7 -> ChessKing(ChessFigureColor.WHITE, board, x, y)
                0 to 6, 1 to 6, 2 to 6, 3 to 6, 4 to 6, 5 to 6, 6 to 6, 7 to 6 -> ChessPawn(
                    ChessFigureColor.WHITE,
                    board,
                    x, y
                )
                else -> {
                    return null
                }
            }
        }
    }
}






