package git.dimitrikvirik.chessgame.model.game

import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import java.time.LocalDateTime
import java.util.*

class ChessBoard {

    val figureLayer: Hashtable<Pair<Int, Int>, Cell<ChessFigure?>> = Hashtable()
    val actionLayer: Hashtable<Pair<Int, Int>, Cell<Action>> = Hashtable()
    val squareLayer: Hashtable<Pair<Int, Int>, Cell<SquareType>> = Hashtable()
    val figureHistory: ArrayList<History<ChessFigure?>> = ArrayList()
    val actionsHistory: ArrayList<History<Action>> = ArrayList()

    fun drawSquareLayer(gridPane: GridPane) {

        squareLayer.forEach {
            val imageView = ImageView("/assert/${it.value.value.resource}")
            imageView.fitWidth = 80.0
            imageView.fitHeight = 80.0
            GridPane.setConstraints(imageView, it.key.first, it.key.second)
            gridPane.children.add(imageView)

        }
    }

    fun drawFigureLayer(gridPane: GridPane) {
        figureLayer.forEach {
            if (it.value.value != null) {
                val color = it.value.value!!.chessFigureType.getByColor(it.value.value!!.chessFigureColor)
                val imageView = ImageView("/assert/$color")
                imageView.fitWidth = 80.0
                imageView.fitHeight = 80.0
                GridPane.setConstraints(imageView, it.key.first, it.key.second)
                gridPane.children.add(imageView)
            }
        }
    }

}

data class Cell<T>(val x: Int, val y: Int, val value: T)

data class History<T>(val value: T, val time: LocalDateTime)

enum class Action {
    MOVED,
    BLOCKED
}

enum class SquareType(val resource: String) {
    WHITE("square_white_1x.png"),
    BLACK("square_black_1x.png")

}