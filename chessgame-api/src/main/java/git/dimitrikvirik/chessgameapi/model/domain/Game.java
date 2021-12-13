package git.dimitrikvirik.chessgameapi.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private UserAccount whitePlayer;

    @ManyToOne
    private UserAccount blackPlayer;

    @ManyToOne
    private UserAccount winnerPlayer;

    @OneToMany(mappedBy = "game")
    private List<GameHistory> history;

}