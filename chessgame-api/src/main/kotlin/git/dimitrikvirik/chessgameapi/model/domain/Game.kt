package git.dimitrikvirik.chessgameapi.model.domain

import javax.persistence.*

@Entity
@Table(name = "game")
class Game(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    @ManyToOne
    val whitePlayer: UserAccount? = null,
    @ManyToOne
    val blackPlayer: UserAccount? = null,
    @ManyToOne
    val winnerPlayer: UserAccount? = null,
    @OneToMany(mappedBy = "game")
    val history: List<GameHistory>? = null
)