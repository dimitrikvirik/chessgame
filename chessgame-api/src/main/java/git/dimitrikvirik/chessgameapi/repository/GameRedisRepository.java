package git.dimitrikvirik.chessgameapi.repository;

import git.dimitrikvirik.chessgameapi.model.redis.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRedisRepository extends CrudRepository<Game, String> {

    Optional<Game> findByBlackPlayerSessionId(String sessionId);
    Optional<Game> findByWhitePlayerSessionId(String sessionId);

}
