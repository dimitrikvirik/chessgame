package git.dimitrikvirik.chessgamedesktop.model.game.figure

class ChessRook(
    chessFigureColor: ChessFigureColor,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.ROOK,
    chessFigureColor,
    x, y
) {

    companion object {
        fun getMovableBlocks(
            x: Int,
            y: Int,
            figure: ChessFigure
        ): List<Pair<Int, Int>> {

            val job: (HashMap<Direction, Pair<Int, Int>>, Int) -> Unit = { moveJobs, i ->
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
            return ChessFigureUtil.Movable.rookAndBishop(figure, job)
        }
    }


    override fun getAllMovableBlocks(): List<Pair<Int, Int>> {
        //TODO replace with king
        return getMovableBlocks(x, y, this)

    }





}