package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.ActionType
import git.dimitrikvirik.chessgamedesktop.model.game.Cell
import git.dimitrikvirik.chessgamedesktop.model.game.History
import git.dimitrikvirik.chessgamedesktop.model.game.LayerContext
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.application.Platform


abstract class ChessFigure(
    override val chessFigureType: ChessFigureType,
    override val color: ChessFigureColor,
    override var x: Int,
    override var y: Int
) :   ChessFigureMove, ChessFigureVirtual(chessFigureType, color, x, y, 1, chessFigureType.getByColor(color)) {

    var hasFirstMove: Boolean = false
    private val layerContext = BeanContext.getBean(LayerContext::class.java)
    val figureLayer = layerContext.figureLayer
    val virtualLayer = layerContext.virtualLayer
    val actionLayer = layerContext.actionLayer

    companion object {
        fun convert(
            type: ChessFigureType,
            figureColor: ChessFigureColor,
            x: Int,
            y: Int
        ): ChessFigure {
            return when (type) {
                ChessFigureType.PAWN -> ChessPawn(figureColor, x, y)
                ChessFigureType.KNIGHT -> ChessKnight(figureColor, x, y)
                ChessFigureType.BISHOP -> ChessBishop(figureColor, x, y)
                ChessFigureType.ROOK -> ChessRook(figureColor, x, y)
                ChessFigureType.QUEEN -> ChessQueen(figureColor, x, y)
                ChessFigureType.KING -> ChessKing(figureColor, x, y)
            }
        }
    }


    private val killableBlocks: ArrayList<Pair<Int, Int>> = arrayListOf()


    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        val allMovableBlocks = getAllMovableBlocks()
        return allMovableBlocks
    }

    override fun move(x: Int, y: Int) {

        Platform.runLater {
            actionLayer.clear()
            if (!hasFirstMove) hasFirstMove = true
            figureLayer.remove(this.x to this.y)
            this.x = x
            this.y = y
            figureLayer[x to y] = this
//            checkEndgame()
//            checkShah()
        }
    }

    fun move(x: Int, y: Int, chessService: ChessService) {
        chessService.send(ChessMessage(this.x to this.y, x to y, ActionType.MOVE))
    }

    fun kill(x: Int, y: Int, chessService: ChessService) {
//        chessService.send(ChessMessage(this.x to this.y, x to y, color, Action.KILL))
    }


    private fun checkEndgame() {
//        val isEndgame = board.figureLayer
//            .filter { it.value?.color == this.color }
//            .mapNotNull { it.value }
//            .all {
//                it.getKillableBlocks().isEmpty() && it.getMovableBlocks().isEmpty()
//            }
//        if (isEndgame) {
//            BeanContext.getBean(ChessService::class.java).send(ChessMessage(0 to 0, 0 to 0, color, Action.ENDGAME))
//        }

    }


    private fun checkShah() {
//
//        val figure = board.figureHistory.last().value
//        if (figure != null) {
//            val king = figure.getAllKillableBlocks().firstOrNull {
//                board.figureLayer[it] is ChessKing
//            }
//            if (king != null) {
//                BeanContext.getBean(ChessService::class.java).send(ChessMessage(king, king, color, Action.SHAH))
//            }
//        }
    }


    private fun checkShahOn(pair: Pair<Int, Int>): Boolean {
//
//        board.figureLayer[x to y] = null
//        this.x = pair.first
//        this.y = pair.second
//        board.figureLayer[x to y] = this
//
//        return board.figureLayer
//            .filter { it.value?.color != this.color }
//            .mapNotNull { it.value }
//            .none { each ->
//                each.isKillableKing()
//            }
        return false

    }


    override fun kill(x: Int, y: Int) {
//        Platform.runLater {
//            board.removeFigure(x, y)
//            move(x, y)
//        }
    }


    protected open fun getAllKillableBlocks(): List<Pair<Int, Int>> {
//        return getAllMovableBlocks().filter {
//            board.figureLayer[it] != null
//        }
        return emptyList()
    }

    override fun getKillableBlocks(): List<Pair<Int, Int>> {
        val allKillableBlocks = getAllKillableBlocks()
        return allKillableBlocks
//
//        return allKillableBlocks.filter {
//            val save = this.x to this.y
//            val savedFigure = board.figureLayer[it]
//            val shahOn = checkShahOn(it)
//            board.figureLayer[x to y] = savedFigure
//            this.x = save.first
//            this.y = save.second
//            board.figureLayer[x to y] = this
//            shahOn
//        }
    }

    private fun isKillableKing(): Boolean {
//        return getAllKillableBlocks().any {
//            board.figureLayer[it] is ChessKing
//        }
        return false
    }


    fun clearKillableBlocks() {
        killableBlocks.clear()
    }


    enum class Direction {
        UP,
        LEFT,
        RIGHT,
        BOTTOM
    }


}






