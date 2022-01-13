package git.dimitrikvirik.chessgamedesktop.core.model

import java.time.LocalDateTime

data class GameHistory(
    val time: LocalDateTime = LocalDateTime.now(),
    val step: Int,
    val from: Coordination,
    val to: Coordination,
    val figureId: String
) {
}