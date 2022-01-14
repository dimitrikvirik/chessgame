package git.dimitrikvirik.chessgamedesktop.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import git.dimitrikvirik.chessgamedesktop.config.ChessStompHandler
import git.dimitrikvirik.chessgamedesktop.config.PlayerStompHandler
import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.ChessPlayer
import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import git.dimitrikvirik.chessgamedesktop.core.model.PlayerMessage
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.util.FileUtil
import javafx.application.Platform
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.socket.messaging.WebSocketStompClient


@Service
class ChessService(
    val chessStompHandler: ChessStompHandler,
    val playerStompHandler: PlayerStompHandler,
    val websocket: WebSocketStompClient
) {


    val restTemplate = RestTemplate()

    lateinit var chessSession: StompSession

    lateinit var playerSession: StompSession

    lateinit var gameId: String

    lateinit var username: String

    var readMode = false


    lateinit var api: String

    fun send(chessMessage: GameMessage) {
        if (!readMode)
            chessSession.send("/app/chessgame/$gameId", chessMessage)

    }


    data class Game(
        val id: String?,
        val stepNumber: Int? = 0,
        var whitePlayer: ChessPlayer?,
        var blackPlayer: ChessPlayer?,
        val messages: List<GameMessage>? = emptyList()
    )


    lateinit var game: Game

    fun create(server: String) {
        this.api = server
        game = restTemplate.postForEntity("http://$api/game", "", Game::class.java).body!!

    }


    fun loadSteps(from: Int = 0) {
        val chessGame = BeanContext.getBean(ChessGame::class.java)


        val entity = restTemplate.getForEntity(
            "http://$api/game/load/$gameId", List::class.java, mapOf("from" to from)
        )
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule.Builder().build())
        objectMapper.registerModule(JavaTimeModule())

        val list = entity.body?.map {
            objectMapper.convertValue(it, GameMessage::class.java)
        } ?: emptyList()


        Platform.runLater {
            if (from == 0) chessGame.currentChessPlayer.set(chessGame.firstChessPlayer)
            list.forEach {
                chessGame.handle(it)
            }
        }

    }

    fun connect(gameId: String, username: String, server: String) {
        FileUtil.createRecord(gameId)
        this.gameId = gameId
        this.username = username
        this.api = server

        chessSession = websocket.connect("ws://$api/ws", chessStompHandler).get()
        playerSession = websocket.connect("ws://$api/ws", playerStompHandler).get()


        playerSession.subscribe("/topic/player/$gameId", playerStompHandler)
        chessSession.subscribe("/topic/chessgame/$gameId", chessStompHandler)
        chessSession.send("/app/player/$gameId", PlayerMessage(username))




        Thread.sleep(1000)
        BeanContext.getBean(GameBoardController::class.java).loadGame()
        loadSteps()
    }


    fun read(filename: String) {
        readMode = true
        BeanContext.getBean(ChessGame::class.java).readMode = true


        Thread {
            FileUtil.readRecord(filename) { gameMessage ->
                Platform.runLater {
                    BeanContext.getBean(ChessGame::class.java).handle(gameMessage)
                }
            }
        }.start()
    }


}