package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor

class ChessRook(
    coordination: Coordination,
    chessFigureColor: ChessFigureColor
) : ChessFigure(
    coordination,
    chessFigureColor.prefix + "Rook",
    chessFigureColor
) {

    companion object {
        fun getMovableBlocks(
            cord: Pair<Int, Int>,
            figure: ChessFigure
        ): List<Pair<Int, Int>> {

            val job: (HashMap<Direction, Pair<Int, Int>>, Int) -> Unit = { moveJobs, i ->
                val x = cord.first
                val y = cord.second

                moveJobs[Direction.UP]?.let {
                    moveJobs[Direction.UP] = x to (y - i)
                }
                moveJobs[Direction.BOTTOM]?.let {
                    moveJobs[Direction.BOTTOM] = x to (y + i)
                }
                moveJobs[Direction.LEFT]?.let {
                    moveJobs[Direction.LEFT] = (x - i) to y
                }
                moveJobs[Direction.RIGHT]?.let {
                    moveJobs[Direction.RIGHT] = (x + i) to y
                }

            }
            return FigureBuilder.Movable.rookAndBishop(figure, job)
        }
    }


    override fun allMovableBlocks(): List<Pair<Int, Int>> {
        return getMovableBlocks(this.cord.x to this.cord.y, this)
    }


}