package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractAction
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.game.figure.ChessKing
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

class SwapAction(coordination: Coordination, targetCoordination: Coordination, override val type: String) :
    AbstractAction(coordination, targetCoordination, "Swap") {
    override fun click(): EventHandler<MouseEvent> {
        return EventHandler<MouseEvent> {
            BeanContext.getBean(ChessGame::class.java).send(this)
        }
    }

    override fun run(figure: AbstractFigure) {
        (figure as ChessKing).swap(coordination.pair)
    }
}