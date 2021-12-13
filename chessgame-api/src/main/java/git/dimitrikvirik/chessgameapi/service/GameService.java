package git.dimitrikvirik.chessgameapi.service;



import git.dimitrikvirik.chessgameapi.model.domain.Game;
import git.dimitrikvirik.chessgameapi.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;



}