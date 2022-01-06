package git.dimitrikvirik.chessgamedesktop.core.model

data class Coordination(
    var x: Int,
    var y: Int,
    var z: Int
) {
    constructor(pair: Pair<Int, Int>, z: Int) : this(pair.first, pair.second, z)
    val pair: Pair<Int, Int>
    get(){
        return x to y
    }

}
