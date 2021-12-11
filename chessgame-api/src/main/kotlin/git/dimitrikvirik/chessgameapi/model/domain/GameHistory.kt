package git.dimitrikvirik.chessgameapi.model.domain

import git.dimitrikvirik.chessgameapi.model.game.Action
import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "game_history")
class GameHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    var time: LocalDateTime? = null,
    @Enumerated var action: Action? = null,
    var from_x: Int? = null,
    var from_y: Int? = null,
    var to_x: Int? = null,
    var to_y: Int? = null,
    @ManyToOne
    var game: Game? = null,
)