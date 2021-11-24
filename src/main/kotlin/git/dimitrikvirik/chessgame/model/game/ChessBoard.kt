package git.dimitrikvirik.chessgame.model.game

import git.dimitrikvirik.chessgame.controller.GameBoardController
import git.dimitrikvirik.chessgame.core.BeanContext
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChessBoard {

    val figureLayer: HashMap<Pair<Int, Int>, ChessFigure?> = HashMap()
    val actionLayer: HashMap<Pair<Int, Int>, Action> = HashMap()
    val squareLayer: HashMap<Pair<Int, Int>, SquareType> = HashMap()
    val figureHistory: ArrayList<History<ChessFigure?>> = ArrayList()
    val actionsHistory: ArrayList<History<Action>> = ArrayList()
    lateinit var gridPane: GridPane

    fun drawSquareLayer() {

        squareLayer.forEach {
            val imageView = ImageView("/assert/${it.value.resource}")
            imageView.fitWidth = 80.0
            imageView.fitHeight = 80.0
            imageView.viewOrder = 3.0
            GridPane.setConstraints(imageView, it.key.first, it.key.second)
            gridPane.children.add(imageView)

        }
    }

    private fun getNodeByRowColumnIndex(row: Int, column: Int, order: Double): Node {
        return gridPane.children.filterIsInstance<ImageView>().first {
            GridPane.getRowIndex(it).equals(row) && GridPane.getColumnIndex(it).equals(column) && it.viewOrder.equals(
                order
            )
        }
    }

    fun clearActionLayer() {
        actionLayer.forEach {
            val imageView = getNodeByRowColumnIndex(it.key.second, it.key.first, 2.0) as ImageView
            gridPane.children.remove(imageView)
        }
        actionLayer.clear()
    }

    fun removeFigure(x: Int, y: Int) {
        val imageView = getNodeByRowColumnIndex(y, x, 1.0) as ImageView
        gridPane.children.remove(imageView)
        figureLayer.remove(x to y)
    }

    fun addFigure(x: Int, y: Int, chessFigure: ChessFigure) {
        val imageView = ImageView("/assert/${chessFigure.chessFigureType.getByColor(chessFigure.color)}")
        imageView.fitWidth = 80.0
        imageView.fitHeight = 80.0
        imageView.viewOrder = 1.0
        imageView.cursor = Cursor.HAND
        imageView.onMouseClicked = EventHandler { event ->
            BeanContext.getBean(GameBoardController::class.java).onFigureClick(event)
        }
        GridPane.setConstraints(imageView, x, y)
        gridPane.children.add(imageView)
        if (figureLayer[x to y] == null) {
            figureLayer[x to y] = chessFigure
        }
    }

    fun drawActionLayer() {
        actionLayer.forEach {
            val imageView = ImageView("/assert/${it.value.resource}")
            imageView.fitWidth = 80.0
            imageView.fitHeight = 80.0
            imageView.viewOrder = 2.0
            if (it.value == Action.MOVE) {
                imageView.cursor = Cursor.HAND
                imageView.onMouseClicked = EventHandler { event ->
                    BeanContext.getBean(GameBoardController::class.java).onMoveBlockClick(event)
                }
            }
            GridPane.setConstraints(imageView, it.key.first, it.key.second)
            gridPane.children.add(imageView)
        }
    }

    fun drawFigureLayer() {
        figureLayer.forEach {
            if (it.value != null) {
                addFigure(it.key.first, it.key.second, it.value!!)
            }
        }
    }

    fun canMove(list: List<Pair<Int, Int>>): List<Pair<Int, Int>>{

      return  list.filter {
            figureLayer[it] == null && it.first in 0..7 && it.second in 0..7
        }
    }

}


data class History<T>(val value: T, val time: LocalDateTime)

enum class Action(val resource: String) {
    MOVE("square_yellow_1x.png"),
    KILL("square_red_1x.png"),
    BLOCK("square_red_1x.png")
}

enum class SquareType(val resource: String) {
    WHITE("square_white_1x.png"),
    BLACK("square_black_1x.png")

}