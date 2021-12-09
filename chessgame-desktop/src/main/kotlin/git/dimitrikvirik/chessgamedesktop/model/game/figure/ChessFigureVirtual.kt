package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.Cell

abstract class ChessFigureVirtual(
    open val chessFigureType: ChessFigureType,
    open val color: ChessFigureColor,
    override var x: Int,
    override var y: Int,
    override var order: Int,
    override var resource: String
) : ChessFigureMove, Cell(x, y, order, "")