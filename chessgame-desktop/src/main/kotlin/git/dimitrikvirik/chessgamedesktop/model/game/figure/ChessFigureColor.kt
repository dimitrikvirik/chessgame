package git.dimitrikvirik.chessgamedesktop.model.game.figure


enum class ChessFigureColor(val prefix: Char) {
    WHITE('W'),
    BLACK('B');

    companion object {
        fun convert(prefix: Char): ChessFigureColor {
            return when (prefix) {
                'W' -> WHITE
                'B' -> BLACK
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }
}
