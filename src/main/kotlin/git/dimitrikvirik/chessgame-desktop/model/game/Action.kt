package git.dimitrikvirik.chessgame.model.game

enum class Action(val resource: String) {
    MOVE("square_yellow_1x.png"),
    KILL("square_red_1x.png")
}
