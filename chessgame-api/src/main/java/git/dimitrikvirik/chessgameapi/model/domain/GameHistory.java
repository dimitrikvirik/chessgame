package git.dimitrikvirik.chessgameapi.model.domain;


import git.dimitrikvirik.chessgameapi.model.game.Action;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public  class GameHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private LocalDateTime time;
    @Enumerated
    private Action action;
    private Integer from_x;
    private Integer from_y;
    private Integer to_x;
    private Integer to_y;
    @ManyToOne
    private Game game;
}
