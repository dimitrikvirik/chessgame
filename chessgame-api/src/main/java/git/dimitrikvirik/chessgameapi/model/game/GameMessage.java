package git.dimitrikvirik.chessgameapi.model.game;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.val;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

@Data
public class GameMessage {
    Pair<Integer, Integer> fromMove;
    Pair<Integer, Integer> toMove;
    String action;
    Integer step;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime sendTime;
}

