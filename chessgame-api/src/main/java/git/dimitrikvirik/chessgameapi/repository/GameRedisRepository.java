package git.dimitrikvirik.chessgameapi.repository;

import git.dimitrikvirik.chessgameapi.model.redis.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRedisRepository extends CrudRepository<Game, String> {
}
