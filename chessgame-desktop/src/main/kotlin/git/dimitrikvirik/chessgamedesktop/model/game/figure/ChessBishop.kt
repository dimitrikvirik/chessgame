package git.dimitrikvirik.chessgamedesktop.model.game.figure


class ChessBishop(
    chessFigureColor: ChessFigureColor,
    override var cord: Pair<Int, Int>
) : ChessFigure(
    ChessFigureType.BISHOP,
    chessFigureColor,
    cord
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
            return ChessFigureUtil.Movable.rookAndBishop(figure, job)
        }

    }

    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        //TODO replace with king

        return getMovableBlocks(cord, this)
    }


}