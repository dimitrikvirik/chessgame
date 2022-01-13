package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.game.action.SwapAction
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor
import java.lang.Math.abs


class ChessKing(
    coordination: Coordination,
    chessFigureColor: ChessFigureColor
) : ChessFigure(
    coordination,
    chessFigureColor.prefix + "King",
    chessFigureColor
) {


    fun swap(pair: Pair<Int, Int>) {
        actionLayer.clear()
        val rook = figureLayer[pair]!! as ChessRook
        figureLayer.remove(this.cord.pair)
        figureLayer.remove(pair)
        if (pair.first == 1) {
            val toMoveRook = (pair.first + 3) to pair.second
            this.cord = Coordination((this.cord.x - 2) to this.cord.y, ObjectIndex.FIGURE)
            this.hasFirstMove = true
            figureLayer[this.cord.pair] = this
            rook.cord = Coordination(toMoveRook, ObjectIndex.FIGURE)
            rook.hasFirstMove = true
            figureLayer[toMoveRook] = rook
        } else {
            val toMoveRook = (pair.first - 2) to pair.second
            this.cord = Coordination((this.cord.x + 2) to this.cord.y, ObjectIndex.FIGURE)
            this.hasFirstMove = true
            figureLayer[this.cord.pair] = this
            rook.cord = Coordination(toMoveRook, ObjectIndex.FIGURE)
            rook.hasFirstMove = true
            figureLayer[toMoveRook] = rook
        }
    }


    override fun drawActions() {
        super.drawActions()
        getSwapBlocks().forEach {
            actionLayer[it] = SwapAction(Coordination(it, ObjectIndex.ACTION), this.cord, "SWAP")
        }

    }


    private fun getSwapBlocks(): List<Pair<Int, Int>> {
        val finalList = mutableListOf<Pair<Int, Int>>()

        if (!hasFirstMove) {
            val rook = figureLayer.values.filterIsInstance<ChessRook>().filter {
                it.color == this.color && !it.hasFirstMove
            }
            val toCustle = rook.filter {
                val toCheck = abs(this.cord.x - it.cord.x) - 1
                var isEmptyBetween = true
                for (i in 1..toCheck) {
                    if (it.cord.x == 8) {
                        val pair = it.cord.x - i to this.cord.y
                        if (figureLayer[pair] != null) {
                            isEmptyBetween = false
                            break
                        } else if (!checkShahOn(pair)) {
                            isEmptyBetween = false
                            break
                        }
                    } else if (it.cord.x == 1) {
                        val pair = it.cord.x + i to this.cord.y
                        if (figureLayer[pair] != null) {
                            isEmptyBetween = false
                            break
                        } else if (!checkShahOn(pair)) {
                            isEmptyBetween = false
                            break
                        }
                    }
                }

                isEmptyBetween
            }.map {
                it.cord.pair
            }
            finalList.addAll(toCustle)
        }
        return finalList
    }

    override fun allMovableBlocks(): List<Pair<Int, Int>> {
        val x = cord.x
        val y = cord.y

        return FigureBuilder.Movable.knightAndKing(
            this, listOf(
                (x - 1 to y - 1),
                (x to y - 1),
                (x + 1 to y - 1),
                (x + 1 to y),
                (x + 1 to y + 1),
                (x to y + 1),
                (x - 1 to y + 1),
                (x - 1 to y)
            )
        )

    }


}