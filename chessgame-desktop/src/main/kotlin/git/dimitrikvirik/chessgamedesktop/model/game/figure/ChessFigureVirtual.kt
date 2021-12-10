package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.Cell

abstract class ChessFigureVirtual(
    open val chessFigureType: ChessFigureType,
    open val color: ChessFigureColor,
    override var cord: Pair<Int, Int>,
    override var order: Int,
    override var resource: String,
) : Cell(cord, order, "")