package git.dimitrikvirik.chessgamedesktop.model.game.figure

import java.lang.IllegalArgumentException


enum class ChessFigureType(private val black: String, private val white: String, val prefix: Char) {
    PAWN("b_pawn_1x.png", "w_pawn_1x.png", 'P'),
    KNIGHT("b_knight_1x.png", "w_knight_1x.png", 'K'),
    BISHOP("b_bishop_1x.png", "w_bishop_1x.png", 'B'),
    ROOK("b_rook_1x.png", "w_rook_1x.png", 'R'),
    QUEEN("b_queen_1x.png", "w_queen_1x.png", 'Q'),
    KING("b_king_1x.png", "w_king_1x.png", 'G');

    fun getByColor(chessFigureColor: ChessFigureColor): String {
        return if (chessFigureColor == ChessFigureColor.BLACK) {
            this.black
        } else this.white
    }

    companion object {
        fun convert(prefix: Char): ChessFigureType {
            return when (prefix) {
                'P' -> PAWN
                'K' -> KNIGHT
                'B' -> BISHOP
                'R' -> ROOK
                'Q' -> QUEEN
                'G' -> KING
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }

}
