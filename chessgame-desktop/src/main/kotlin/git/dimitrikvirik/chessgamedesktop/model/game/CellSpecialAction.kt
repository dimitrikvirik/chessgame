package git.dimitrikvirik.chessgamedesktop.model.game

class CellSpecialAction(
   cord: Pair<Int, Int>,
   actionType: ActionType
): Cell(cord, 2, actionType.resource) {
}