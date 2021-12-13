package git.dimitrikvirik.chessgameapi.repository;

import git.dimitrikvirik.chessgameapi.model.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GameRepository extends JpaRepository<Game, Long> {
}
