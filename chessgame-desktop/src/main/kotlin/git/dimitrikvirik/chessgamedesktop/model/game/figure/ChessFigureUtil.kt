package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard

class ChessFigureUtil {
    object Movable {
        fun knightAndKing(
            board: ChessBoard,
            figure: ChessFigure,
            moves: List<Pair<Int, Int>>
        ): List<Pair<Int, Int>> {

            val groupBy = moves.filter {
                it.first >= 0 && it.second >= 0
            }.groupBy {
                val chessFigure = board.figureLayer[it]
                if (chessFigure != null && chessFigure.color != figure.color) "movable"
                else if (chessFigure == null) "movable"
                else "other"
            }

            return groupBy["movable"] ?: emptyList()
        }

        fun rookAndBishop(
            board: ChessBoard,
            thisFigure: ChessFigure,
            executable: (HashMap<ChessFigure.Direction, Pair<Int, Int>>, Int) -> Unit
        ): List<Pair<Int, Int>> {


            val moveJobs: HashMap<ChessFigure.Direction, Pair<Int, Int>> = hashMapOf(
                ChessFigure.Direction.UP to (0 to 0),
                ChessFigure.Direction.LEFT to (0 to 0),
                ChessFigure.Direction.RIGHT to (0 to 0),
                ChessFigure.Direction.BOTTOM to (0 to 0),
            )

            val list: ArrayList<Pair<Int, Int>> = arrayListOf()

            for (i in 0..7) {

                executable(moveJobs, i)
                val toDelete: ArrayList<ChessFigure.Direction> = arrayListOf()

                moveJobs.forEach {
                    if (it.value.first < 0 || it.value.second < 0) {
                        toDelete.add(it.key)
                        return@forEach
                    }

                    val chessFigure = board.figureLayer[it.value]
                    if (chessFigure == null) {
                        list.add(it.value)
                    } else if (chessFigure != thisFigure) {
                        toDelete.add(it.key)
                        if (chessFigure.color != thisFigure.color) {
                            list.add(it.value)
                        }
                    }
                }
                toDelete.forEach {
                    moveJobs.remove(it)
                }
            }
            return list
        }

    }

    companion object {

        fun getByNumber(x: Int, y: Int, board: ChessBoard): ChessFigure? {
            return when (x to y) {
                7 to 0, 0 to 0 -> ChessRook(ChessFigureColor.BLACK, board, x, y)
                1 to 0, 6 to 0 -> ChessKnight(ChessFigureColor.BLACK, board, x, y)
                2 to 0, 5 to 0 -> ChessBishop(ChessFigureColor.BLACK, board, x, y)
                3 to 0 -> ChessQueen(ChessFigureColor.BLACK, board, x, y)
                4 to 0 -> ChessKing(ChessFigureColor.BLACK, board, x, y)
                0 to 1, 1 to 1, 2 to 1, 3 to 1, 4 to 1, 5 to 1, 6 to 1, 7 to 1 -> ChessPawn(
                    ChessFigureColor.BLACK,
                    board,
                    x, y
                )
                7 to 7, 0 to 7 -> ChessRook(ChessFigureColor.WHITE, board, x, y)
                1 to 7, 6 to 7 -> ChessKnight(ChessFigureColor.WHITE, board, x, y)
                2 to 7, 5 to 7 -> ChessBishop(ChessFigureColor.WHITE, board, x, y)
                3 to 7 -> ChessQueen(ChessFigureColor.WHITE, board, x, y)
                4 to 7 -> ChessKing(ChessFigureColor.WHITE, board, x, y)
                0 to 6, 1 to 6, 2 to 6, 3 to 6, 4 to 6, 5 to 6, 6 to 6, 7 to 6 -> ChessPawn(
                    ChessFigureColor.WHITE,
                    board,
                    x, y
                )
                else -> {
                    return null
                }
            }
        }
    }
}