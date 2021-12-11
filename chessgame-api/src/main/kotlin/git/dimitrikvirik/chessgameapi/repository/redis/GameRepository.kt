package git.dimitrikvirik.chessgameapi.repository.redis

import git.dimitrikvirik.chessgameapi.model.redis.Game
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameRepository : CrudRepository<Game?, String?> {

}