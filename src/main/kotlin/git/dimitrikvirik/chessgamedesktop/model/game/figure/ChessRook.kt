package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard

class ChessRook(
    chessFigureColor: ChessFigureColor,
    board: ChessBoard,
    x: Int,
    y: Int
) : ChessFigure(
    ChessFigureType.ROOK,
    chessFigureColor,
    board,
    x, y
) {

    companion object {
        fun getMovableBlocks(
            x: Int,
            y: Int,
            board: ChessBoard,
            figure: ChessFigure,
            killableBlocks: ArrayList<Pair<Int, Int>>
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
            return movableBlocksForRookBishop(x, y, board, figure, killableBlocks, job)
        }
    }


    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        //TODO replace with king
        return getMovableBlocks(x, y, board, this, killableBlocks)

    }



}