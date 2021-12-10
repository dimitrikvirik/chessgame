package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import git.dimitrikvirik.chessgamedesktop.model.game.LayerContext
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.application.Platform


abstract class ChessFigure(
    override val chessFigureType: ChessFigureType,
    override var color: ChessFigureColor,
    override var cord: Pair<Int, Int>
) : ChessFigureVirtual(chessFigureType, color, cord, 1, chessFigureType.getByColor(color)) {


    private val layerContext = BeanContext.getBean(LayerContext::class.java)
    val figureLayer = layerContext.figureLayer
    val actionLayer = layerContext.actionLayer
    val shahLayer = layerContext.shahLayer

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

    fun getMovableBlocks(): List<Pair<Int, Int>> {
        val saved = this.cord
        val filter = getAllMovableBlocks().filter {
            checkShahOn(it)
        }
        figureLayer.remove(this.cord)
        this.cord = saved
        figureLayer[saved] = this
        return filter
    }

    open fun move(cord: Pair<Int, Int>) {
        actionLayer.clear()
        shahLayer.clear()
        figureLayer.remove(this.cord)
        this.cord = cord
        figureLayer[cord] = this
        checkEndgame()
        checkShah()
    }

    fun kill(cord: Pair<Int, Int>) {
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
            chessService.send(ChessMessage(cord, cord, ActionType.ENDGAME))
        }

    }


    private fun checkShah() {
        val isShah = figureLayer.filterValues {
            it == this
        }.values.any {
            it.isKillableKing()
        }
        if (isShah) {
            val chessService = BeanContext.getBean(ChessService::class.java)
            chessService.send(ChessMessage(cord, cord, ActionType.SHAH))
        }
    }


    private fun checkShahOn(pair: Pair<Int, Int>): Boolean {

        figureLayer.remove(this.cord)
        this.cord = pair
        figureLayer[pair] = this

        return figureLayer
            .filter { it.value.color != this.color }
            .mapNotNull { it.value }
            .none { each ->
                each.isKillableKing()
            }

    }


    protected open fun getAllKillableBlocks(): List<Pair<Int, Int>> {
        return getAllMovableBlocks().filter {
            figureLayer[it] != null
        }
    }

    fun getKillableBlocks(): List<Pair<Int, Int>> {


        return getAllKillableBlocks().filter {
            val save = this.cord
            val savedFigure = figureLayer[it]
            val shahOn = checkShahOn(it)
            figureLayer[it] = savedFigure!!
            this.cord = save
            figureLayer[save] = this
            shahOn
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






