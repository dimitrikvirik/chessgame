package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor


class ChessBishop(
    coordination: Coordination,
    chessFigureColor: ChessFigureColor
) : ChessFigure(
    coordination,
    chessFigureColor.prefix + "Bishop",
    chessFigureColor
) {


    companion object {
        fun getMovableBlocks(
            cord: Pair<Int, Int>,
            figure: ChessFigure
        ): List<Pair<Int, Int>> {
            val x = cord.first
            val y = cord.second

            val job: (HashMap<Direction, Pair<Int, Int>>, Int) -> Unit = { moveJobs, i ->
                moveJobs[Direction.UP]?.let {
                    moveJobs[Direction.UP] = (x - i) to (y - i)
                }
                moveJobs[Direction.BOTTOM]?.let {
                    moveJobs[Direction.BOTTOM] = (x - i) to (y + i)
                }
                moveJobs[Direction.LEFT]?.let {
                    moveJobs[Direction.LEFT] = (x + i) to (y - i)
                }
                moveJobs[Direction.RIGHT]?.let {
                    moveJobs[Direction.RIGHT] = (x + i) to (y + i)
                }
            }
            return FigureBuilder.Movable.rookAndBishop(figure, job)
        }

    }

    override fun allMovableBlocks(): List<Pair<Int, Int>> {
        return getMovableBlocks(this.cord.x to this.cord.y, this)
    }


}