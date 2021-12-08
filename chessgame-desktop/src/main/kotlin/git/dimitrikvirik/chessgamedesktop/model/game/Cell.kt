package git.dimitrikvirik.chessgamedesktop.model.game

open class Cell(open var x: Int, open var y: Int, open var order: Int, open var resource: String) {

    var toDraw: Boolean = true
    var toRemove: Boolean = false
    val prefix: Char
        get() {
            return when (this) {
//                is ChessFigure -> 'F'
                is Action -> 'A'
                is Square -> 'S'
                else -> ' '
            }
        }

}