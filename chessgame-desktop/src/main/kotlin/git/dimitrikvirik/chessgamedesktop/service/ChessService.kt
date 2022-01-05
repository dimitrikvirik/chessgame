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
import org.springframework.web.client.RestTemplate
import org.springframework.web.socket.messaging.WebSocketStompClient


data class ChessMessage(
    var fromMove: Pair<Int, Int>,
    var toMove: Pair<Int, Int>,
    var actionType: ActionType,
    val step: Int
)


@Service
class ChessService(
    val chessGame: ChessGame,
    val chessStompHandler: ChessStompHandler
) {


    @Autowired
    lateinit var websocket: WebSocketStompClient

    val restTemplate = RestTemplate()

    lateinit var session: StompSession

    lateinit var gameId: String


    @Value("\${api.uri}")
    lateinit var api: String

    fun send(chessMessage: ChessMessage) {
        session.send("/app/chessgame/$gameId", chessMessage)

    }


    data class Game(
        val id: String?,
        val stepNumber: Int? = 0,
        val messages: List<ChessMessage>? = emptyList()
    ) {
    }

    lateinit var game: Game

    fun create() {
        game = restTemplate.postForEntity("http://$api/game", "", Game::class.java).body!!

    }

    fun connect(gameId: String) {
        this.gameId = gameId
        startGame(gameId)
        val sessionHandler: StompSessionHandler = chessStompHandler
        session = websocket.connect("ws://$api/ws", sessionHandler).get()
        session.subscribe("/topic/chessgame/$gameId", sessionHandler)
    }


    fun startGame(gameId: String): ChessGame {
        chessGame.startGame(User("White Player"), User("Black Player"))
        return chessGame
    }


}