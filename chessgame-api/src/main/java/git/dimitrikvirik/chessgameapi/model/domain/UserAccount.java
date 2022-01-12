//package git.dimitrikvirik.chessgameapi.model.domain;
//
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Data
//public class UserAccount {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long id;
//    private String username;
//    private String email;
//    private String keycloakId;
//    @ManyToMany
//    private List<UserAccount> friends;
//    @OneToMany(mappedBy = "whitePlayer")
//    private List<Game> whitePlayerGames;
//    @OneToMany(mappedBy = "blackPlayer")
//
//    private List<Game> blackPlayerGames;
//    @OneToMany(mappedBy = "winnerPlayer")
//
//    private List<Game> wonGames;
//}