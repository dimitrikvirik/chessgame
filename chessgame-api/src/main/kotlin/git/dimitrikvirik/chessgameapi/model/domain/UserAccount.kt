package git.dimitrikvirik.chessgameapi.model.domain

import javax.persistence.*

@Entity
@Table(name = "user_account")
class UserAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    val username: String? = null,
    @ManyToMany
    val friends: List<UserAccount>? = null,
    @OneToMany(mappedBy = "whitePlayer")
    val whitePlayerGames: List<Game>? = null,
    @OneToMany(mappedBy = "blackPlayer")
    val blackPlayerGames: List<Game>? = null,
    @OneToMany(mappedBy = "winnerPlayer")
    val wonGames: List<Game>? = null
)