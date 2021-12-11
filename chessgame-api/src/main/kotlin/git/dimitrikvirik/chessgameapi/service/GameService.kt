package git.dimitrikvirik.chessgameapi.service

import git.dimitrikvirik.chessgameapi.model.redis.Game
import git.dimitrikvirik.chessgameapi.repository.redis.GameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameService(
    val gameRepository: GameRepository
) {
    fun create(): Game {
        val game = Game(UUID.randomUUID().toString())
        return gameRepository.save(game)
    }
}