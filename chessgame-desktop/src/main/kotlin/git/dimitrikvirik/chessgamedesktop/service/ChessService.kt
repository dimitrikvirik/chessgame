package git.dimitrikvirik.chessgamedesktop.service

import git.dimitrikvirik.chessgamedesktop.config.ChessStompHandler
import git.dimitrikvirik.chessgamedesktop.model.domain.User
import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import git.dimitrikvirik.chessgamedesktop.model.game.ChessGame
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Service
import org.springframework.web.socket.messaging.WebSocketStompClient


data class ChessMessage(
    var fromMove: Pair<Int, Int>,
    var toMove: Pair<Int, Int>,
//    var playerColor: ChessFigureColor,
    var actionType: ActionType
)


@Service
class ChessService() {


    @Autowired
    lateinit var websocket: WebSocketStompClient

    lateinit var session: StompSession

    lateinit var gameId: String

    var chessGame: ChessGame = ChessGame()

    @Value("\${api.uri}")
    lateinit var api: String

    fun send(chessMessage: ChessMessage) {
        session.send("/app/chessgame/$gameId", chessMessage)
    }


    fun connect(gameId: String) {
        this.gameId = gameId
        startGame(gameId)
        val chessStompHandler = ChessStompHandler()
        chessStompHandler.chessGame = chessGame
        val sessionHandler: StompSessionHandler = chessStompHandler
        session = websocket.connect("ws://$api/ws", sessionHandler).get()
        session.subscribe("/topic/chessgame/$gameId", sessionHandler)
    }


    fun startGame(gameId: String): ChessGame {
        chessGame.startGame(User("White Player"), User("Black Player"))
        return chessGame
    }


}