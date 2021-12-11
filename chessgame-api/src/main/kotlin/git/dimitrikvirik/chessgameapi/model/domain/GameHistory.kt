package git.dimitrikvirik.chessgameapi.model.domain

import git.dimitrikvirik.chessgameapi.model.game.Action
import git.dimitrikvirik.chessgameapi.repository.annotation.Entity
import java.time.LocalDateTime
import java.util.*

@Entity
data class GameHistory(
    val id: UUID,
    val time: LocalDateTime,
    val action: Action,
    val from_x: Int,
    val from_y: Int,
    val to_x: Int,
    val to_y: Int
) {
}