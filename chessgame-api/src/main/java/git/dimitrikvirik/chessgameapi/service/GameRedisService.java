package git.dimitrikvirik.chessgameapi.service;

import git.dimitrikvirik.chessgameapi.model.redis.Game;
import git.dimitrikvirik.chessgameapi.repository.GameRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRedisService {

    private final GameRedisRepository gameRepository;

    public Game create() {
        return gameRepository.save(new Game());
    }
}
