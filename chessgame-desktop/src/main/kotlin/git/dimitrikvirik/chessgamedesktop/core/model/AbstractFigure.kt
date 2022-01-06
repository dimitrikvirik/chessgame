package git.dimitrikvirik.chessgamedesktop.core.model

abstract class AbstractFigure(coordination: Coordination, prefix: String) :
    GameObject(coordination, "F$prefix") {
    var pair: Pair<Int, Int>
        get() {
            return cord.x to cord.y
        }
        set(value) {
            cord.x = value.first
            cord.y = value.second
        }

    val killedFigures: List<AbstractFigure> = emptyList()
    val moves: List<Pair<Int, Int>> = emptyList()



    abstract fun movableBlocks(): List<Pair<Int, Int>>
    abstract fun killableBlocks(): List<Pair<Int, Int>>
    abstract fun allMovableBlocks(): List<Pair<Int, Int>>
    abstract fun allKillableBlocks(): List<Pair<Int, Int>>
    abstract fun move(coordination: Pair<Int, Int>)
    abstract fun kill(coordination: Pair<Int, Int>)

}