package git.dimitrikvirik.chessgame.model.game.figure

interface ChessFigureMove {
    fun getMovableBlocks(): List<Pair<Int, Int>>
    fun getKillableBlocks(): List<Pair<Int, Int>>
    fun move(x: Int, y: Int)
    fun kill(x: Int, y: Int)

}