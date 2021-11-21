package git.dimitrikvirik.chessgame.model.game

import java.lang.IllegalArgumentException

data class ChessFigure(val chessFigureType: ChessFigureType, val chessFigureColor: ChessFigureColor) {

    companion object {
        fun getByNumber(x: Int, y: Int): ChessFigure? {
            return when (x to y) {
                7 to 0, 0 to 0 -> ChessFigure(ChessFigureType.ROOK, ChessFigureColor.BLACK)
                1 to 0, 6 to 0 -> ChessFigure(ChessFigureType.KNIGHT, ChessFigureColor.BLACK)
                2 to 0, 5 to 0 -> ChessFigure(ChessFigureType.BISHOP, ChessFigureColor.BLACK)
                3 to 0 -> ChessFigure(ChessFigureType.QUEEN, ChessFigureColor.BLACK)
                4 to 0 -> ChessFigure(ChessFigureType.KING, ChessFigureColor.BLACK)
                0 to 1, 1 to 1, 2 to 1, 3 to 1, 4 to 1, 5 to 1, 6 to 1, 7 to 1 -> ChessFigure(
                    ChessFigureType.PAWN,
                    ChessFigureColor.BLACK
                )
                7 to 7, 0 to 7 -> ChessFigure(ChessFigureType.ROOK, ChessFigureColor.WHITE)
                1 to 7, 6 to 7 -> ChessFigure(ChessFigureType.KNIGHT, ChessFigureColor.WHITE)
                2 to 7, 5 to 7 -> ChessFigure(ChessFigureType.BISHOP, ChessFigureColor.WHITE)
                3 to 7 -> ChessFigure(ChessFigureType.QUEEN, ChessFigureColor.WHITE)
                4 to 7 -> ChessFigure(ChessFigureType.KING, ChessFigureColor.WHITE)
                0 to 6, 1 to 6, 2 to 6, 3 to 6, 4 to 6, 5 to 6, 6 to 6, 7 to 6 -> ChessFigure(
                    ChessFigureType.PAWN,
                    ChessFigureColor.WHITE
                )

                else -> {
                    return null
                }
            }
        }
    }

    enum class ChessFigureType(val black: String, val white: String) {
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
}

enum class ChessFigureColor {
    WHITE,
    BLACK
}