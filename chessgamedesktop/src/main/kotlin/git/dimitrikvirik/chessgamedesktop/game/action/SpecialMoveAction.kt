package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination

class SpecialMoveAction(coordination: Coordination, override val type: String) :
    SpecialAction(coordination, "MoveS", type) {

}