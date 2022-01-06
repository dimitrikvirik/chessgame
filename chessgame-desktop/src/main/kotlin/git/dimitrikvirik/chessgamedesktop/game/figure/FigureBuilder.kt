package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor

object FigureBuilder {

    object Movable {

        fun knightAndKing(
            figure: ChessFigure,
            moves: List<Pair<Int, Int>>
        ): List<Pair<Int, Int>> {
            val figureLayer = figure.figureLayer
            val groupBy = moves.filter {
                it.first in 1..8 && it.second in 0..7
            }.groupBy {
                val chessFigure = figureLayer[it] as ChessFigure?
                if (chessFigure != null && chessFigure.color != figure.color) "movable"
                else if (chessFigure == null) "movable"
                else "other"
            }


            return groupBy["movable"] ?: emptyList()
        }

        fun rookAndBishop(
            figure: ChessFigure,
            executable: (HashMap<ChessFigure.Direction, Pair<Int, Int>>, Int) -> Unit
        ): List<Pair<Int, Int>> {
            val figureLayer = figure.figureLayer

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
                    if (it.value.first !in 1..8 || it.value.second !in 0..7) {
                        toDelete.add(it.key)
                        return@forEach
                    }

                    val chessFigure = figureLayer[it.value] as ChessFigure?
                    if (chessFigure == null) {
                        list.add(it.value)
                    } else if (chessFigure != figure) {
                        toDelete.add(it.key)
                        if (chessFigure.color != figure.color) {
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

    fun getByNumber(x: Int, y: Int): ChessFigure? {
        val cord = Coordination(x to y, ObjectIndex.FIGURE)

        return when (x to y) {
            8 to 0, 1 to 0 -> ChessRook(cord, ChessFigureColor.BLACK)
            2 to 0, 7 to 0 -> ChessKnight(cord, ChessFigureColor.BLACK)
            3 to 0, 6 to 0 -> ChessBishop(cord, ChessFigureColor.BLACK)
            4 to 0 -> ChessQueen(cord, ChessFigureColor.BLACK)
            5 to 0 -> ChessKing(cord, ChessFigureColor.BLACK)
            1 to 1, 2 to 1, 3 to 1, 4 to 1, 5 to 1, 6 to 1, 7 to 1, 8 to 1 -> ChessPawn(
                cord,
                ChessFigureColor.BLACK
            )
            8 to 7, 1 to 7 -> ChessRook(cord, ChessFigureColor.WHITE)
            2 to 7, 7 to 7 -> ChessKnight(cord, ChessFigureColor.WHITE)
            3 to 7, 6 to 7 -> ChessBishop(cord, ChessFigureColor.WHITE)
            4 to 7 -> ChessQueen(cord, ChessFigureColor.WHITE)
            5 to 7 -> ChessKing(cord, ChessFigureColor.WHITE)
            1 to 6, 2 to 6, 3 to 6, 4 to 6, 5 to 6, 6 to 6, 7 to 6, 8 to 6 -> ChessPawn(
                cord,
                ChessFigureColor.WHITE,
            )
            else -> {
                return null
            }
        }

    }

}