package git.dimitrikvirik.chessgamedesktop.game

import git.dimitrikvirik.chessgamedesktop.core.GameEngine
import git.dimitrikvirik.chessgamedesktop.core.model.*
import git.dimitrikvirik.chessgamedesktop.game.action.*
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import git.dimitrikvirik.chessgamedesktop.util.FileUtil
import org.springframework.stereotype.Component

@Component
class ChessGame(
    val chessService: ChessService
) : GameEngine() {


    override fun handle(gameMessage: GameMessage) {



        if(currentStep + 1 != gameMessage.step){
            throw IllegalArgumentException("Wrong step")
        }
        currentStep = gameMessage.step

        if (!gameMessage.action.startsWith("BECOME")) {
            if (currentPlayer.value == firstPlayer) {
                currentPlayer.set(secondPlayer)
            } else {
                currentPlayer.set(firstPlayer)
            }
        }
        val action = when (gameMessage.action) {
            "MOVE" -> {
                MoveAction(
                    Coordination(gameMessage.toMove, ObjectIndex.ACTION),
                    Coordination(gameMessage.fromMove, ObjectIndex.ACTION), "MOVE"
                )
            }
            "KILL" -> {
                KillAction(
                    Coordination(gameMessage.toMove, ObjectIndex.ACTION),
                    Coordination(gameMessage.fromMove, ObjectIndex.ACTION), "KILL"
                )
            }
            "SHAH" -> {
                ShahAction(
                    Coordination(gameMessage.fromMove, ObjectIndex.ACTION), "SHAH"
                )
            }
            "SWAP" -> {
                SwapAction(
                    Coordination(gameMessage.toMove, ObjectIndex.ACTION),
                    Coordination(gameMessage.fromMove, ObjectIndex.ACTION), "SWAP"
                )
            }
            "ENDGAME" -> {
                EndgameAction(
                    Coordination(gameMessage.fromMove, ObjectIndex.ACTION), "ENDGAME"
                )
            }
            else -> {
                if (gameMessage.action.startsWith("BECOME-")) {
                    BecomeAction(Coordination(gameMessage.fromMove, -10), gameMessage.action)
                } else

                    throw IllegalArgumentException()
            }
        }
        val figure =
            figureLayer[gameMessage.fromMove] as AbstractFigure? ?: figureLayer[gameMessage.toMove] as AbstractFigure

        action.run(figure)
        if (gameMessage.action == "SHAH") {
            if (currentPlayer.value == firstPlayer) {
                currentPlayer.set(secondPlayer)
            } else {
                currentPlayer.set(firstPlayer)
            }
        }


    }

    fun send(action: AbstractAction) {
        chessService.send(GameMessage(action.targetCoordination.pair, action.cord.pair, action.type, currentStep))


    }


}