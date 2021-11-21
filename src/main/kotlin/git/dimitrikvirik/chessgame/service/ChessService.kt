package git.dimitrikvirik.chessgame.service

import git.dimitrikvirik.chessgame.model.domain.User
import git.dimitrikvirik.chessgame.model.game.*
import org.springframework.stereotype.Service


@Service
class ChessService() {

    lateinit var chessGame: ChessGame

    init {
        chessGame = ChessGame(
            ChessPlayer(ChessFigureColor.WHITE, User("wPlayer")),
            ChessPlayer(ChessFigureColor.BLACK, User("bPlayer")),
            false,
            Invitation.PUBLIC,
            ChessBoard()
        )
    }




}