package git.dimitrikvirik.chessgameapi.model.param;


import lombok.Data;

@Data
public class GameJoinParam {

    private String senderPlayerName;
    private String whitePlayerName;
    private String blackPlayerName;
    private Boolean whiteConnected;
    private Boolean blackConnected;
}
