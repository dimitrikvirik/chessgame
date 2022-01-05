package git.dimitrikvirik.chessgameapi.model.param;


import git.dimitrikvirik.chessgameapi.model.enums.ChessPlayerColor;
import lombok.Data;

@Data
public class GameJoinParam {

    private String gameId;

    private ChessPlayerColor color;


}
