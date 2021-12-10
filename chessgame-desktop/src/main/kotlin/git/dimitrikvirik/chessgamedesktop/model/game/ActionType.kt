package git.dimitrikvirik.chessgamedesktop.model.game


data class Action(val action: ActionType, override var cord: Pair<Int, Int>) : Cell(cord, 2, action.resource)


enum class ActionType(val resource: String, val prefix: Char) {
    MOVE("square_yellow_1x.png", 'M'),
    KILL("square_red_1x.png", 'K'),
    SHAH("square_red_1x.png", 'S'),
    ENDGAME("", 'E');

    companion object {
        fun convert(prefix: Char): ActionType {

            return when (prefix) {
                'M' -> MOVE
                'K' -> KILL
                'S' -> SHAH
                'E' -> ENDGAME
                else -> {
                    throw  IllegalArgumentException()
                }
            }
        }
    }
}