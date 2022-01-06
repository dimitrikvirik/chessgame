package git.dimitrikvirik.chessgamedesktop.service

import git.dimitrikvirik.chessgamedesktop.config.ChessStompHandler
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.util.FileUtil
import javafx.application.Platform
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.socket.messaging.WebSocketStompClient


@Service
class ChessService(
    val chessStompHandler: ChessStompHandler
) {


    @Autowired
    lateinit var websocket: WebSocketStompClient

    val restTemplate = RestTemplate()

    lateinit var session: StompSession

    lateinit var gameId: String

    var readMode: Boolean = false


    @Value("\${api.uri}")
    lateinit var api: String

    fun send(chessMessage: GameMessage) {
        if (!readMode)
            session.send("/app/chessgame/$gameId", chessMessage)

    }


    data class Game(
        val id: String?,
        val stepNumber: Int? = 0,
        val messages: List<GameMessage>? = emptyList()
    ) {
    }

    lateinit var game: Game

    fun create() {
        game = restTemplate.postForEntity("http://$api/game", "", Game::class.java).body!!

    }

    fun connect(gameId: String) {
        FileUtil.createRecord()
        this.gameId = gameId
        val sessionHandler: StompSessionHandler = chessStompHandler
        session = websocket.connect("ws://$api/ws", sessionHandler).get()
        session.subscribe("/topic/chessgame/$gameId", sessionHandler)

    }

    fun read(filename: String) {
        readMode = true
        BeanContext.getBean(ChessGame::class.java).readMode = true
        Thread{
            FileUtil.readRecord(filename) { gameMessage ->
                Platform.runLater {
                    BeanContext.getBean(ChessGame::class.java).handle(gameMessage)
                }
            }
        }.start()
    }


}