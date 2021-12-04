package git.dimitrikvirik.chessgamedesktop.model.game.figure

interface ChessFigureMove {
    fun getAllMovableBlocks(): List<Pair<Int, Int>>
    fun getRealMovableBlocks(): List<Pair<Int, Int>>
    fun getKillableBlocks(): List<Pair<Int, Int>>
    fun move(x: Int, y: Int)
    fun kill(x: Int, y: Int)

}