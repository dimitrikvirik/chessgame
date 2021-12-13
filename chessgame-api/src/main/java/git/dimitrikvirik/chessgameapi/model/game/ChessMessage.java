package git.dimitrikvirik.chessgameapi.model.game;

import lombok.Data;
import org.springframework.data.util.Pair;

@Data
public class ChessMessage {
    Pair<Integer, Integer> fromMove;
    Pair<Integer, Integer> toMove;
    ChessFigureColor playerColor;
    Action action;
}

