package git.dimitrikvirik.chessgameapi.model.redis;

import git.dimitrikvirik.chessgameapi.model.Message;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;


@RedisHash("Game")
@Data
public class Game {

    private String id;
    private Integer stepNumber;

    private List<Message> messages;

}