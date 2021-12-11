package git.dimitrikvirik.chessgameapi.model.domain

import git.dimitrikvirik.chessgameapi.repository.annotation.Entity
import java.util.*

@Entity
data class UserAccount(
    val id: UUID,
    val username: String,
    val friends: List<UserAccount>,
    val games: List<GameHistory>
)