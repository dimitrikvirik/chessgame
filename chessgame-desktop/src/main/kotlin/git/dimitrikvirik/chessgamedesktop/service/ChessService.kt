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

data class Message(val message: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (!message.contentEquals(other.message)) return false

        return true
    }

    override fun hashCode(): Int {
        return message.contentHashCode()
    }
}


data class ChessMessage(
    var fromMove: Pair<Int, Int>,
    var toMove: Pair<Int, Int>,
    var actionType: ActionType
) {
    val message: Message
        get() {
            val byteArray = ByteArray(5)

            byteArray[0] = fromMove.first.toByte()
            byteArray[1] = fromMove.second.toByte()
            byteArray[2] = toMove.first.toByte()
            byteArray[3] = toMove.second.toByte()
            byteArray[4] = actionType.prefix.code.toByte()

            return Message(byteArray)
        }

    constructor(message: ByteArray) : this(0 to 0, 0 to 0, ActionType.MOVE) {
        fromMove = (message[0].toInt()) to (message[1].toInt())
        toMove = (message[2].toInt()) to (message[3].toInt())
        actionType = ActionType.convert(message[4].toInt().toChar())

    }
}


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
        session.send("/app/chessgame/$gameId", chessMessage.message)
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