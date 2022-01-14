package git.dimitrikvirik.chessgamedesktop.core.model

data class Coordination(
    var x: Int,
    var y: Int,
    var z: Int
) {
    constructor(pair: Pair<Int, Int>, z: Int) : this(pair.first, pair.second, z)

    val pair: Pair<Int, Int>
        get() {
            return x to y
        }

    override fun toString(): String {
        return "$x$y$z"
    }

    companion object {
        fun toChess(pair: Pair<Int, Int>): String {
            if (pair.first == 0 && pair.second == 0) {
                return "empty"
            }
            val y = 8 - pair.second
            val x = Char(64 + pair.first)
            return "${x}${y}"
        }

        fun fromChess(move: String): Pair<Int, Int> {
            if (move == "empty") return 0 to 0
            val y =8 - move[1].toString().toInt()
            val x = move[0].code - 64
            return Pair(x, y)
        }
    }

}
