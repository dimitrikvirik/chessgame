package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessKing
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane


class ChessBoard {

    val figureLayer = HashMap<Pair<Int, Int>, ChessFigure?>()
    val actionLayer = HashMap<Pair<Int, Int>, Action>()
    val squareLayer = HashMap<Pair<Int, Int>, SquareType>()
    val figureHistory = ArrayList<History<ChessFigure?>>()
    val actionsHistory = ArrayList<History<Action>>()

    lateinit var gridPane: GridPane
    lateinit var chessGame: ChessGame

    fun drawSquareLayer() {

        squareLayer.forEach {

            val imageView = ImageView("/assert/${it.value.resource}")

            imageView.fitWidth = gridPane.prefWidth / 8
            imageView.fitHeight = gridPane.prefHeight / 8
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
        Platform.runLater {
            gridPane.children.remove(imageView)
        }

        figureLayer.remove(x to y)
        figureLayer[x to y] = null
    }

    fun addFigure(x: Int, y: Int, chessFigure: ChessFigure) {
        val imageView = ImageView("/assert/${chessFigure.chessFigureType.getByColor(chessFigure.color)}")
        imageView.fitWidth = gridPane.prefWidth / 8
        imageView.fitHeight = gridPane.prefHeight / 8
        imageView.viewOrder = 1.0

        imageView.onMouseClicked = EventHandler { event ->
            BeanContext.getBean(GameBoardController::class.java).onFigureClick(event)
        }
        GridPane.setConstraints(imageView, x, y)
        Platform.runLater {
            gridPane.children.add(imageView)
        }
        if (chessFigure.color == ChessFigureColor.BLACK) {
            imageView.cursorProperty().bind(chessGame.blackPlayer.cursor)
        } else {
            imageView.cursorProperty().bind(chessGame.whitePlayer.cursor)
        }
        if (chessFigure is ChessKing) {
            chessGame.currentPlayer.king = chessFigure
        }
        if (figureLayer[x to y] == null) {
            figureLayer[x to y] = chessFigure
        }
    }

    fun drawActionLayer() {
        actionLayer.forEach {
            val imageView = ImageView("/assert/${it.value.resource}")
            imageView.fitWidth = gridPane.prefWidth / 8
            imageView.fitHeight = gridPane.prefHeight / 8
            imageView.viewOrder = 2.0
            when (it.value) {
                Action.MOVE -> {
                    imageView.cursor = Cursor.HAND
                    imageView.onMouseClicked = EventHandler { event ->
                        BeanContext.getBean(GameBoardController::class.java).onMoveBlockClick(event)
                    }
                }
                Action.KILL -> {
                    imageView.cursor = Cursor.HAND
                    imageView.onMouseClicked = EventHandler { event ->
                        BeanContext.getBean(GameBoardController::class.java).onKillBlockClick(event)
                    }
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


    fun fixBlocks(blocks: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val list = blocks.toMutableList()
        return list.filter {
            it.first in 0..7 && it.second in 0..7
        }
    }

}

