package git.dimitrikvirik.chessgameapi.service;

import git.dimitrikvirik.chessgameapi.model.redis.Game;
import git.dimitrikvirik.chessgameapi.repository.GameRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GameRedisService {

    private final GameRedisRepository gameRepository;

    public Game create() {
        return gameRepository.save(new Game());
    }

    public Game update(Game game) {
        return gameRepository.save(game);
    }

    public Game get(String id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found!")
        );
    }

    public Game getBySessionId(String sessionId) {
        return gameRepository.findByWhitePlayerSessionId(sessionId).orElse(
                gameRepository.findByBlackPlayerSessionId(sessionId).orElse(null)
        );
    }

}
