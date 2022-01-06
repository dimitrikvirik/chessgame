package git.dimitrikvirik.chessgameapi.model.redis;

import git.dimitrikvirik.chessgameapi.model.game.GameMessage;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;


@RedisHash("Game")
@Data
public class Game {

    @Indexed
    private String id;
    private Integer stepNumber;
    private ChessPlayer whitePlayer;
    private ChessPlayer blackPlayer;
    private List<GameMessage> messages = new ArrayList<>();

}