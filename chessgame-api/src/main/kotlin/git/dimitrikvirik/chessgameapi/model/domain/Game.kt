package git.dimitrikvirik.chessgameapi.model.domain

import git.dimitrikvirik.chessgameapi.repository.annotation.Entity
import java.util.*

@Entity
data class Game(
    val id: UUID,
    val whitePlayer: UserAccount,
    val blackPlayer: UserAccount,
    val winnerPlayer: UserAccount,
    val history: List<GameHistory>
) {
}