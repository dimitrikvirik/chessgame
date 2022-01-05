package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import git.dimitrikvirik.chessgamedesktop.model.game.LayerContext
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import kotlin.math.abs


abstract class ChessFigure(
    override val chessFigureType: ChessFigureType,
    override var color: ChessFigureColor,
    override var cord: Pair<Int, Int>
) : ChessFigureVirtual(chessFigureType, color, cord, 1, chessFigureType.getByColor(color)) {

    var hasFirstMove = false
    private val layerContext = BeanContext.getBean(LayerContext::class.java)
    val figureLayer = layerContext.figureLayer
    val actionLayer = layerContext.actionLayer
    val specialLayer = layerContext.specialLayer

    companion object {
        fun convert(
            type: ChessFigureType,
            figureColor: ChessFigureColor,
            x: Int,
            y: Int
        ): ChessFigure {
            val cord = x to y
            return when (type) {
                ChessFigureType.PAWN -> ChessPawn(figureColor, cord)
                ChessFigureType.KNIGHT -> ChessKnight(figureColor, cord)
                ChessFigureType.BISHOP -> ChessBishop(figureColor, cord)
                ChessFigureType.ROOK -> ChessRook(figureColor, cord)
                ChessFigureType.QUEEN -> ChessQueen(figureColor, cord)
                ChessFigureType.KING -> ChessKing(figureColor, cord)
            }
        }
    }


    abstract fun getAllMovableBlocks(): List<Pair<Int, Int>>

    fun getSwapBlocks(): List<Pair<Int, Int>> {
        val finalList = mutableListOf<Pair<Int, Int>>()

        if (this is ChessKing && !hasFirstMove) {
            val rook = figureLayer.values.filter {
                it.color == this.color && it is ChessRook && !it.hasFirstMove
            }
            val toCustle = rook.filter {
                val toCheck = abs(this.cord.first - it.cord.first) - 1
                var isEmptyBetween = true
                for (i in 1..toCheck) {
                    if (it.cord.first == 8) {
                        val pair = it.cord.first - i to this.cord.second
                        if (figureLayer[pair] != null ) {
                            isEmptyBetween = false
                            break
                        }
                        else if(!checkShahOn(pair)){
                            isEmptyBetween = false
                            break
                        }
                    } else if (it.cord.first == 1) {
                        val pair = it.cord.first + i to this.cord.second
                        if (figureLayer[pair] != null)  {
                            isEmptyBetween = false
                            break
                        }
                        else if(!checkShahOn(pair)){
                            isEmptyBetween = false
                            break
                        }
                    }
                }

                isEmptyBetween
            }.map {
                it.cord
            }
            finalList.addAll(toCustle)
        }
        return finalList
    }

    fun getMovableBlocks(): List<Pair<Int, Int>> {
        val finalList = mutableListOf<Pair<Int, Int>>()
        val filter = getAllMovableBlocks().filter {
            figureLayer[it] == null
        }.filter {
            checkShahOn(it)
        }

        finalList.addAll(filter)
        return finalList
    }

    open fun move(cord: Pair<Int, Int>) {
        actionLayer.clear()
        specialLayer.clear()
        figureLayer.remove(this.cord)
        this.cord = cord
        figureLayer[cord] = this
        checkEndgame()
        checkShah()
        hasFirstMove = true
    }

    open fun kill(cord: Pair<Int, Int>) {
        figureLayer.remove(cord)
        move(cord)
    }


    private fun checkEndgame() {
        val isEndgame = figureLayer
            .filter { it.value.color != this.color }
            .mapNotNull { it.value }
            .all {
                it.getKillableBlocks().isEmpty() && it.getMovableBlocks().isEmpty()
            }
        if (isEndgame) {
            val chessService = BeanContext.getBean(ChessService::class.java)
            chessService.send(ChessMessage(cord, cord, ActionType.ENDGAME, chessService.chessGame.currentStep))
        }

    }


    fun checkShah() {
        val isShah = figureLayer.filterValues {
            it == this
        }.values.any {
            it.isKillableKing()
        }
        if (isShah) {
            val chessService = BeanContext.getBean(ChessService::class.java)
            chessService.send(ChessMessage(cord, cord, ActionType.SHAH, chessService.chessGame.currentStep))
        }
    }


    protected fun checkShahOn(pair: Pair<Int, Int>, forKillable: Boolean = false): Boolean {

        val existedFigure = figureLayer[pair]
        val savedFigure = this
        val saveCord = this.cord

        figureLayer[pair] = this

        val filter = figureLayer
            .filter { it.value.color != this.color }
            .mapNotNull { it.value }
            .none { each ->
                each.isKillableKing()
            }
        if (existedFigure != null) figureLayer[existedFigure.cord] = existedFigure
        if(forKillable)
         savedFigure.cord = saveCord
        figureLayer[saveCord] = savedFigure
        return filter
    }


    protected open fun getAllKillableBlocks(): List<Pair<Int, Int>> {
        return getAllMovableBlocks().filter {
            figureLayer[it] != null
        }
    }

    fun getKillableBlocks(): List<Pair<Int, Int>> {
        return getAllKillableBlocks().filter {
            checkShahOn(it, true)
        }
    }

    fun isKillableKing(): Boolean {
        return getAllKillableBlocks().any {
            figureLayer[it] is ChessKing
        }
    }


    fun clearKillableBlocks() {
//        killableBlocks.clear()
    }


    enum class Direction {
        UP,
        LEFT,
        RIGHT,
        BOTTOM
    }


}






