package git.dimitrikvirik.chessgameapi.model.redis;

import git.dimitrikvirik.chessgameapi.model.Message;
import git.dimitrikvirik.chessgameapi.model.game.ChessMessage;
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
    private List<ChessMessage> messages = new ArrayList<>();

}