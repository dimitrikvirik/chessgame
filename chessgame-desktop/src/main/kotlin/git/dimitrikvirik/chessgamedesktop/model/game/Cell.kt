package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure

open class Cell(open var cord: Pair<Int, Int>, open var order: Int, open var resource: String) {

    val prefix: Char
        get() {
            return when (this) {
                is ChessFigure -> 'F'
                is Action -> 'A'
                is Square -> 'S'
                is Shah -> 'H'
                else -> ' '
            }
        }

}