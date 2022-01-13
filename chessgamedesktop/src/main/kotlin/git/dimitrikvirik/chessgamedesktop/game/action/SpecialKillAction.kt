package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination

class SpecialKillAction(coordination: Coordination, override val type: String) :
    SpecialAction(coordination, "KillS", type) {


}