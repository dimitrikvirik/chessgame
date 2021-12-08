package git.dimitrikvirik.chessgamedesktop.model.game


data class Action(val action: ActionType, override var x: Int, override var y: Int) : Cell(x, y, 2, action.resource)


enum class ActionType(val resource: String, val prefix: Char) {
    MOVE("square_yellow_1x.png", 'M'),
    KILL("square_red_1x.png", 'K'),
    SHAH("square_red_1x.png", 'S'),
    ENDGAME("", 'E');

    companion object {
        fun convert(prefix: Char): ActionType {

            return when (prefix) {
                'M' -> ActionType.MOVE
                'K' -> ActionType.KILL
                'S' -> ActionType.SHAH
                'E' -> ActionType.ENDGAME
                else -> {
                    throw  IllegalArgumentException()
                }
            }
        }
    }
}