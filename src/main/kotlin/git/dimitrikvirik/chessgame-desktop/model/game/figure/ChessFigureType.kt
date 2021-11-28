package git.dimitrikvirik.chessgame.model.game.figure


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
