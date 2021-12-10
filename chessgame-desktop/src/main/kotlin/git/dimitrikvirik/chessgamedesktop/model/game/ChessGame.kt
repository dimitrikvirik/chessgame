package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.domain.User
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessKing
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import javafx.application.Platform
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
                currentPlayer = whitePlayer
            }
            ChessFigureColor.WHITE -> {
                whitePlayer.canMove = false
                whitePlayer.cursor.set(Cursor.DEFAULT)

                blackPlayer.canMove = true
                blackPlayer.cursor.set(Cursor.HAND)
                currentPlayer = blackPlayer
            }
        }
    }

    fun handleMessage(message: ChessMessage) {

        val figureLayer = BeanContext.getBean(LayerContext::class.java).figureLayer

        goNextPlayer(figureLayer[message.fromMove]?.color!!)

        when (message.actionType) {
            ActionType.MOVE -> figureLayer[message.fromMove]?.move(message.toMove)
            ActionType.KILL -> figureLayer[message.fromMove]?.kill(message.toMove)
            ActionType.SHAH -> {
                val color = figureLayer[message.fromMove]?.color
                figureLayer.filterValues {
                    it.color != color
                }.values.filterIsInstance<ChessKing>().first().shah()
            }
            ActionType.ENDGAME -> {
                winnerPlayer = if (currentPlayer.chessFigureColor == ChessFigureColor.WHITE) {
                    blackPlayer
                } else {
                    whitePlayer
                }

                BeanContext.getBean(GameBoardController::class.java).endgame()
                return
            }
        }


    }

}


