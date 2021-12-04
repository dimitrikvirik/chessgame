package git.dimitrikvirik.chessgamedesktop.model.game.figure

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.ChessBoard
import git.dimitrikvirik.chessgamedesktop.model.game.History
import git.dimitrikvirik.chessgamedesktop.service.Action
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.application.Platform


abstract class ChessFigure(
    val chessFigureType: ChessFigureType,
    open val color: ChessFigureColor,
    open val board: ChessBoard,
    open var x: Int,
    open var y: Int
) : ChessFigureMove {

    var hasFirstMove: Boolean = false
    protected val killableBlocks: ArrayList<Pair<Int, Int>> = arrayListOf()


    override fun getMovableBlocks(): List<Pair<Int, Int>> {
        val allMovableBlocks = getAllMovableBlocks()
        val fixBlocks = board.fixBlocks(allMovableBlocks)
        return fixBlocks.filter {
            board.figureLayer[it] == null
        }.filter {
            val save = this.x to this.y
            val shahOn = checkShahOn(it)
            board.figureLayer[x to y] = null
            this.x = save.first
            this.y = save.second
            board.figureLayer[x to y] = this
            shahOn
        }


    }

    override fun move(x: Int, y: Int) {

        Platform.runLater {

            board.clearShahAction()
            board.figureHistory.add(History(this))
            if (!hasFirstMove) hasFirstMove = true
            board.removeFigure(this.x, this.y)
            board.clearActionLayer()
            this.x = x
            this.y = y
            board.addFigure(x, y, this)
            checkEndgame()
            checkShah()
        }
    }

    fun move(x: Int, y: Int, chessService: ChessService) {
        chessService.send(ChessMessage(this.x to this.y, x to y, color, Action.MOVE))
    }

    fun kill(x: Int, y: Int, chessService: ChessService) {
        chessService.send(ChessMessage(this.x to this.y, x to y, color, Action.KILL))
    }


    private fun checkEndgame() {
        val isEndgame = board.figureLayer
            .filter { it.value?.color == this.color }
            .mapNotNull { it.value }
            .all {
                it.getKillableBlocks().isEmpty() && it.getMovableBlocks().isEmpty()
            }
        if (isEndgame) {
            BeanContext.getBean(ChessService::class.java).send(ChessMessage(0 to 0, 0 to 0, color, Action.ENDGAME))
        }

    }


    private fun checkShah() {

        val figure = board.figureHistory.last().value
        if (figure != null) {
            val king = figure.getAllKillableBlocks().firstOrNull {
                board.figureLayer[it] is ChessKing
            }
            if (king != null) {
                BeanContext.getBean(ChessService::class.java).send(ChessMessage(king, king, color, Action.SHAH))
            }
        }
    }


    private fun checkShahOn(pair: Pair<Int, Int>): Boolean {

        board.figureLayer[x to y] = null
        this.x = pair.first
        this.y = pair.second
        board.figureLayer[x to y] = this

        return board.figureLayer
            .filter { it.value?.color != this.color }
            .mapNotNull { it.value }
            .none { each ->
                each.isKillableKing()
            }

    }


    override fun kill(x: Int, y: Int) {
        Platform.runLater {
            board.removeFigure(x, y)
            move(x, y)
        }
    }


    protected open fun getAllKillableBlocks(): List<Pair<Int, Int>> {
        return getAllMovableBlocks().filter {
            board.figureLayer[it] != null
        }
    }

    override fun getKillableBlocks(): List<Pair<Int, Int>> {
        val allKillableBlocks = getAllKillableBlocks()

        return allKillableBlocks.filter {
            val save = this.x to this.y
            val savedFigure = board.figureLayer[it]
            val shahOn = checkShahOn(it)
            board.figureLayer[x to y] = savedFigure
            this.x = save.first
            this.y = save.second
            board.figureLayer[x to y] = this
            shahOn
        }
    }

    private fun isKillableKing(): Boolean {
        return getAllKillableBlocks().any {
            board.figureLayer[it] is ChessKing
        }
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






