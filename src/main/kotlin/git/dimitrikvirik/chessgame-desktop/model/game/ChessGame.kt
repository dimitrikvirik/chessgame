package git.dimitrikvirik.chessgame.model.game

import git.dimitrikvirik.chessgame.model.domain.User
import git.dimitrikvirik.chessgame.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgame.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgame.model.game.figure.ChessKing
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
        chessBoard = ChessBoard()
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
    //TODO filter movable and killable blocks on shah
}


