package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.domain.User
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessKing
import git.dimitrikvirik.chessgamedesktop.service.Action
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import javafx.scene.Cursor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.Delegates


class ChessGame {
    lateinit var whitePlayer: ChessPlayer
    lateinit var blackPlayer: ChessPlayer

    var isPublic by Delegates.notNull<Boolean>()
    lateinit var invitedFrom: Invitation
    lateinit var chessBoard: ChessBoard
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


    fun goNextPlayer() {

        currentPlayer.canMove = false
        currentPlayer.cursor.set(Cursor.DEFAULT)

        currentPlayer = if (currentPlayer == whitePlayer) {
            blackPlayer
        } else whitePlayer



        currentPlayer.canMove = true
        currentPlayer.cursor.set(Cursor.HAND)
    }

    fun checkShah() {
//        logger.info("testing shah...")
        val figure = chessBoard.figureHistory.last().value
        if(figure != null){
           if( figure.getKillableBlocks().any {
                chessBoard.figureLayer[it] is ChessKing
            }){
               logger.warn("Shah!")
           }
        }
    }

    fun handleMessage(message: ChessMessage) {

        if(message.action == Action.MOVE) {
            chessBoard.figureLayer[message.fromMove]?.move(message.toMove.first, message.toMove.second)
        }

    }
    //TODO filter movable and killable blocks on shah
}


