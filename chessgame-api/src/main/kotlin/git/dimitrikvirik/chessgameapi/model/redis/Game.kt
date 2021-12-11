package git.dimitrikvirik.chessgameapi.model.redis

import git.dimitrikvirik.chessgameapi.model.Message
import org.springframework.data.redis.core.RedisHash

@RedisHash("Game")
data class Game(
    val id: String?,
    val stepNumber: Int = 0,
    val messages: List<Message> = emptyList()
) {
}