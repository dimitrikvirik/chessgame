package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure

open class Cell(open var x: Int, open var y: Int, open var order: Int, open var resource: String) {

    val prefix: Char
        get() {
            return when (this) {
                is ChessFigure -> 'F'
                is Action -> 'A'
                is Square -> 'S'
                else -> ' '
            }
        }

}