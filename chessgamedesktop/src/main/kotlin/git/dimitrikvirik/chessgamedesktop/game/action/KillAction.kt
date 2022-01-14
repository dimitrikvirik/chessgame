package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.AssertLoader
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractAction
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

class KillAction(coordination: Coordination, targetCoordination: Coordination, override val type: String) :
    AbstractAction(coordination, targetCoordination, "Kill") {

    override fun click(): EventHandler<MouseEvent> {
        return EventHandler<MouseEvent> {
            BeanContext.getBean(ChessGame::class.java).send(this)
        }
    }
//49d3e68f-ff50-4b51-ad8b-f19f9b9928af
    override fun run(figure: AbstractFigure) {
        figure.kill(coordination.pair)
        val specialSquareLayer = BeanContext.getBean(ChessGame::class.java).specialActionLayer
        specialSquareLayer[coordination.pair] = SpecialKillAction(Coordination(coordination.pair, ObjectIndex.S_ACTION), "SKILL")
        specialSquareLayer[targetCoordination.pair] = SpecialMoveAction(Coordination(targetCoordination.pair, ObjectIndex.S_ACTION), "SMOVE")
    BeanContext.getBean(AssertLoader::class.java).loadSound("kill")
    }


}