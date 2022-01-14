package git.dimitrikvirik.chessgamedesktop.game.figure

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.Layer
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.game.action.EndgameAction
import git.dimitrikvirik.chessgamedesktop.game.action.KillAction
import git.dimitrikvirik.chessgamedesktop.game.action.MoveAction
import git.dimitrikvirik.chessgamedesktop.game.action.ShahAction
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor
import javafx.beans.Observable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.input.MouseEvent


abstract class ChessFigure(
    coordination: Coordination,
    prefix: String,
    val color: ChessFigureColor,
) : AbstractFigure(coordination, "C$prefix") {

    var hasFirstMove = false
    val chessGame: ChessGame = BeanContext.getBean(ChessGame::class.java)

    val figureLayer: Layer = chessGame.figureLayer
    val actionLayer: Layer = chessGame.actionLayer

    private val cursor = SimpleObjectProperty(Cursor.DEFAULT)

    private val canMove: Boolean
        get() {
            return (((chessGame.currentChessPlayer.value.color == ChessFigureColor.WHITE && color == ChessFigureColor.WHITE)
                    || (chessGame.currentChessPlayer.value.color == ChessFigureColor.BLACK && color == ChessFigureColor.BLACK))
                    ) && !chessGame.ended && !chessGame.readMode && chessGame.currentChessPlayer.value == chessGame.joinedChessPlayer
        }


    init {
        chessGame.currentChessPlayer.addListener { o: Observable ->
            if (canMove) {
                cursor.set(Cursor.HAND)
            } else cursor.set(Cursor.DEFAULT)
        }
    }


    override fun click(): EventHandler<MouseEvent> {
        return EventHandler<MouseEvent> {
            if (canMove) {
                drawActions()
            }

        }
    }

    protected open fun drawActions() {
        actionLayer.clear()
        movableBlocks().forEach {
            actionLayer[it] = MoveAction(Coordination(it, ObjectIndex.ACTION), this.cord, "MOVE")
        }
        killableBlocks().forEach {
            actionLayer[it] = KillAction(Coordination(it, ObjectIndex.ACTION), this.cord, "KILL")
        }
    }


    override fun hover(): ObservableValue<Cursor> {
        return cursor
    }

    override fun move(coordination: Pair<Int, Int>) {
        chessGame.actionLayer.clear()
        chessGame.specialActionLayer.clear()
        chessGame.figureLayer.remove(this.cord.pair)
        this.cord = Coordination(coordination, ObjectIndex.FIGURE)
        chessGame.figureLayer[coordination] = this

        hasFirstMove = true
        checkEndgame()
        checkShah()
        this.pair = coordination
    }

    override fun movableBlocks(): List<Pair<Int, Int>> {
        val finalList = mutableListOf<Pair<Int, Int>>()
        val filter = allMovableBlocks().filter {
            chessGame.figureLayer[it] == null
        }.filter {
            checkShahOn(it)
        }
        finalList.addAll(filter)
        return finalList
    }

    override fun allKillableBlocks(): List<Pair<Int, Int>> {
        return allMovableBlocks().filter {
            figureLayer[it] != null
        }
    }

    override fun killableBlocks(): List<Pair<Int, Int>> {
        return allKillableBlocks().filter {
            checkShahOn(it)
        }
    }


    override fun kill(coordination: Pair<Int, Int>) {
        figureLayer.remove(coordination)
        move(coordination)
    }


    protected fun checkShahOn(coordination: Pair<Int, Int>): Boolean {

        val existedFigure = figureLayer[coordination] as ChessFigure?
        val savedFigure = this
        val saveCord = this.pair

        figureLayer.remove(saveCord)
        figureLayer.remove(coordination)
        this.cord = Coordination(coordination, ObjectIndex.FIGURE)
        figureLayer[coordination] = this

        val filter = figureLayer.values
            .filterIsInstance<ChessFigure>()
            .filter { it.color != this.color }
            .none { each ->
                each.isKillableKing()
            }


        figureLayer.remove(savedFigure.cord.pair)
        savedFigure.cord = Coordination(saveCord, ObjectIndex.FIGURE)
        figureLayer[saveCord] = savedFigure

        if (existedFigure != null) {
            figureLayer[existedFigure.pair] = existedFigure
        }
        return filter
    }


    private fun checkEndgame() {
        val isEndgame = figureLayer.values.filterIsInstance<ChessFigure>()
            .filter { it.color != this.color }
            .all {
                it.killableBlocks().isEmpty() && it.movableBlocks().isEmpty()
            }
        if (isEndgame) {
            EndgameAction(this.cord, "ENDGAME").run(this)
        }

    }

    fun checkShah() {
        val isShah = figureLayer.values.filterIsInstance<ChessFigure>().any {
            it.isKillableKing()
        }
        if (isShah) {
            ShahAction(this.cord).run(this)

        }
    }


    private fun isKillableKing(): Boolean {
        return allKillableBlocks().any {
            figureLayer[it] is ChessKing
        }
    }


    enum class Direction {
        UP,
        LEFT,
        RIGHT,
        BOTTOM
    }


}






