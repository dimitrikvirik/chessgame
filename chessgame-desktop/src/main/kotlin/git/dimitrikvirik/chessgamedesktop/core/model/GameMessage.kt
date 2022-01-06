package git.dimitrikvirik.chessgamedesktop.core.model

import java.time.LocalDateTime

data class GameMessage(
    var fromMove: Pair<Int, Int>,
    var toMove: Pair<Int, Int>,
    val action: String,
    val step: Int,
    val sendTime: LocalDateTime? = null
) {


}