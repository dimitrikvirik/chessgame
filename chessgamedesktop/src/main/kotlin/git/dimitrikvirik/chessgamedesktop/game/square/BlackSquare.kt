package git.dimitrikvirik.chessgamedesktop.game.square

import git.dimitrikvirik.chessgamedesktop.core.model.AbstractSquare
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex

class BlackSquare(pair: Pair<Int, Int>) :
    AbstractSquare(Coordination(pair, ObjectIndex.SQUARE), "B") {
}