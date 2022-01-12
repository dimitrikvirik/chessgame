package git.dimitrikvirik.chessgameapi.model.redis;

import git.dimitrikvirik.chessgameapi.model.enums.ChessPlayerColor;
import lombok.Data;

@Data
public class ChessPlayer {

    private ChessPlayerColor color;

    private String userId;

    private Boolean connected;

}
