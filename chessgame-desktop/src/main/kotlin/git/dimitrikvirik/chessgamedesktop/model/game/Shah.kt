package git.dimitrikvirik.chessgamedesktop.model.game

class Shah(
   cord: Pair<Int, Int>
): Cell(cord, 2, ActionType.SHAH.resource) {
}