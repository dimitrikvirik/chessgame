package git.dimitrikvirik.chessgamedesktop.service

import git.dimitrikvirik.chessgamedesktop.config.ChessStompHandler
import git.dimitrikvirik.chessgamedesktop.model.domain.User
import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard
import git.dimitrikvirik.chessgamedesktop.model.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.model.game.SquareType
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Service
import org.springframework.web.socket.messaging.WebSocketStompClient


enum class Action(val resource: String) {

    MOVE("square_yellow_1x.png"),
    KILL("square_red_1x.png"),
    SHAH("square_red_1x.png"),
    ENDGAME("")
}

data class ChessMessage(
    var fromMove: Pair<Int, Int>,
    var toMove: Pair<Int, Int>,
    var playerColor: ChessFigureColor,
    var action: Action
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
        chessGame.chessBoard = ChessBoard()
        chessGame.chessBoard.chessGame = chessGame


        for (i in 0..7) {
            for (j in 0..7) {
                chessGame.chessBoard.squareLayer[i to j] = if ((i + j) % 2 == 0) SquareType.WHITE else SquareType.BLACK
                chessGame.chessBoard.figureLayer[i to j] = ChessFigure.getByNumber(i, j, chessGame.chessBoard)

            }
        }

        chessGame.startGame(User("White Player"), User("Black Player"))
        return chessGame
    }


}