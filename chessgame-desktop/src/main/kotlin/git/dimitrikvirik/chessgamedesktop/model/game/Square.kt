package git.dimitrikvirik.chessgamedesktop.model.game


data class Square(val square: SquareType, override var cord: Pair<Int, Int>) : Cell(cord, 3, square.resource)

enum class SquareType(val resource: String, val prefix: Char) {
    WHITE("square_white_1x.png", 'W'),
    BLACK("square_black_1x.png", 'B');

    companion object {
        fun convert(prefix: Char): SquareType {
            return when (prefix) {
                'W' -> WHITE
                'B' -> BLACK
                else -> {
                    throw  IllegalArgumentException()
                }
            }
        }
    }

}