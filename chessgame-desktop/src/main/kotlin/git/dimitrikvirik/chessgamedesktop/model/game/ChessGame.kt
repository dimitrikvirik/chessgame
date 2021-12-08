package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.domain.User
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import javafx.scene.Cursor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.Delegates


class ChessGame {
    lateinit var whitePlayer: ChessPlayer
    lateinit var blackPlayer: ChessPlayer

    var isPublic by Delegates.notNull<Boolean>()
    lateinit var invitedFrom: Invitation
    lateinit var currentPlayer: ChessPlayer
    var winnerPlayer: ChessPlayer? = null

    private val logger: Logger = LoggerFactory.getLogger(ChessGame::class.java)

    fun startGame(whiteUser: User, blackUser: User) {
        whitePlayer = ChessPlayer(ChessFigureColor.WHITE, whiteUser)
        blackPlayer = ChessPlayer(ChessFigureColor.BLACK, blackUser)
        isPublic = false
        invitedFrom = Invitation.PUBLIC
        currentPlayer = whitePlayer
        whitePlayer.canMove = true
        whitePlayer.cursor.set(Cursor.HAND)
        blackPlayer.cursor.set(Cursor.DEFAULT)

    }

    fun endgame(winnerPlayer: ChessPlayer) {
        this.winnerPlayer = winnerPlayer
    }


    private fun goNextPlayer(color: ChessFigureColor) {

        when (color) {
            ChessFigureColor.BLACK -> {
                blackPlayer.canMove = false
                blackPlayer.cursor.set(Cursor.DEFAULT)

                whitePlayer.canMove = true
                whitePlayer.cursor.set(Cursor.HAND)
            }
            ChessFigureColor.WHITE -> {
                whitePlayer.canMove = false
                whitePlayer.cursor.set(Cursor.DEFAULT)

                blackPlayer.canMove = true
                blackPlayer.cursor.set(Cursor.HAND)
            }
        }
    }

//    fun handleMessage(message: ChessMessage) {
//
//
//
//        when (message) {
//            ActionType.MOVE -> chessBoard.figureLayer[message.fromMove]?.move(message.toMove.first, message.toMove.second)
//            ActionType.KILL -> chessBoard.figureLayer[message.fromMove]?.kill(message.toMove.first, message.toMove.second)
//            ActionType.SHAH -> {
//                (chessBoard.figureLayer[message.fromMove] as ChessKing).shah()
//            }
//            ActionType.ENDGAME ->{
//                Platform.runLater {
//                    winnerPlayer = this.currentPlayer
//                    BeanContext.getBean(GameBoardController::class.java).endgame()
//                }
//                return
//            }
//        }
//        goNextPlayer(message.playerColor)
//        println(message.playerColor)
//
//
//
//
//    }

}


