package git.dimitrikvirik.chessgame.service

import git.dimitrikvirik.chessgame.config.MyStompSessionHandler
import git.dimitrikvirik.chessgame.model.domain.User
import git.dimitrikvirik.chessgame.model.game.*
import git.dimitrikvirik.chessgame.model.game.figure.ChessFigureColor
import javafx.scene.Cursor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.MappingJacksonInputMessage
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Service
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.*


@Service
class ChessService() {


    //TODO get chessGame

    final val chessGame: ChessGame = ChessGame()

    fun connect(url: String){

            val client: WebSocketClient = StandardWebSocketClient()

            val stompClient = WebSocketStompClient(client)


            val sessionHandler: StompSessionHandler = MyStompSessionHandler()
            stompClient.connect(url, sessionHandler)

            Scanner(System.`in`).nextLine() // Don't close immediately.

    }


    init {
        chessGame.startGame(User("White Player"), User("Black Player"))

    }


}