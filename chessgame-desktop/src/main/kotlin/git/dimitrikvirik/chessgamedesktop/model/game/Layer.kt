package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureType
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import kotlin.math.sqrt

class Layer<T : Cell>(private val gridPane: GridPane, private val prefix: Char) : MutableMap<Pair<Int, Int>, T> {


    private fun getNode(idPrefix: String): ImageView? {
        return gridPane.children.filterIsInstance<ImageView>().firstOrNull {
            it.id.startsWith(idPrefix)
        }
    }

    private fun getNodeByCord(x: Int, y: Int, idPrefix: Char): ImageView? {
        return gridPane.children.filterIsInstance<ImageView>().firstOrNull {
            GridPane.getColumnIndex(it) == x && GridPane.getRowIndex(it) == y && it.id.startsWith(prefix)
        }
    }

    private fun getNodes(idPrefix: String): List<ImageView> {
        return gridPane.children.filterIsInstance<ImageView>().filter {
            it.id.startsWith(idPrefix)
        }
    }


    private fun imageViewToCell(imageView: ImageView): T {
        val it = imageView.id.toCharArray()
        var type: Any? = null
        when (it[0]) {
            'A' -> {
                type = ActionType.convert(it[1])
            }
            'F' -> {
                type = ChessFigureType.convert(it[1])
            }
            'S' -> {
                type = SquareType.convert(it[1])
            }
        }
        val x = Character.getNumericValue(it[2])
        val y = Character.getNumericValue(it[3])
        return when (type) {
            is ActionType -> Action(type, x, y) as T
            is ChessFigureType -> {
                val figureColor = ChessFigureColor.convert(it[4])
                ChessFigure.convert(type, figureColor, x, y) as T
            }
            is SquareType -> Square(type, x, y) as T
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    private fun imageViewToCell(imageViews: List<ImageView>): List<T> {
        return imageViews.map {
            imageViewToCell(it)
        }
    }


    private fun cellToId(cell: Cell): String {
        val result = StringBuilder()
        when (cell) {
            is Action -> result.append('A')
            is ChessFigure -> result.append('F')
            is Square -> result.append('S')
        }
        when(cell){
            is Action -> result.append(cell.action.prefix)
            is ChessFigure -> result.append(cell.chessFigureType.prefix)
            is Square -> result.append(cell.square.prefix)
        }
        result.append(cell.x)
        result.append(cell.y)
        if (cell is ChessFigure) {
            result.append(cell.color.prefix)
        }
        return result.toString()
    }

    private fun cellToImageView(cell: Cell): ImageView {
        val imageView = ImageView("/assert/${cell.resource}")
        imageView.fitWidth = gridPane.prefWidth / 8
        imageView.fitHeight = gridPane.prefHeight / 8
        imageView.viewOrder = cell.order.toDouble()
        imageView.id = cellToId(cell)
        GridPane.setConstraints(imageView,  cell.x, cell.y)
        return imageView
    }


    private fun cellToImageView(cells: List<Cell>): List<ImageView> {
        return cells.map {
            cellToImageView(it)
        }
    }


    private fun getLayer(type: Char = ' '): Map<Pair<Int, Int>, T> {
        return imageViewToCell(getNodes(type.toString().replace(" ", ""))).associateBy {
            (it.x to it.y)
        }
    }

     fun getAll(): MutableMap<Pair<Int, Int>, T> {
        return getLayer(prefix).toMutableMap()
    }

    override val entries: MutableSet<MutableMap.MutableEntry<Pair<Int, Int>, T>>
        get() = getAll().entries
    override val keys: MutableSet<Pair<Int, Int>>
        get() = getAll().keys
    override val size: Int
        get() = getAll().size
    override val values: MutableCollection<T>
        get() = getAll().values

    override fun containsKey(key: Pair<Int, Int>): Boolean {
        return getAll().containsKey(key)
    }

    override fun containsValue(value: T): Boolean {
        return getAll().containsValue(value)
    }

    override fun isEmpty(): Boolean {
        return getAll().isEmpty()
    }

    override fun get(key: Pair<Int, Int>): T? {
        val nodeByCord = getNodeByCord(key.first, key.second, prefix)
        return if (nodeByCord != null)
            imageViewToCell(nodeByCord)
        else null
    }

    override fun clear() {
        getAll().keys.forEach {
            remove(it)
        }
    }

    override fun put(key: Pair<Int, Int>, value: T): T {
        val imageView = cellToImageView(value)
        if (value is ChessFigure) {
            imageView.onMouseClicked = EventHandler { event ->
                BeanContext.getBean(GameBoardController::class.java).onFigureClick(event)
            }
            val chessGame = BeanContext.getBean(GameBoardController::class.java).chessGame
            if (value.color == ChessFigureColor.BLACK) {
                imageView.cursorProperty().bind(chessGame.blackPlayer.cursor)
            } else {
                imageView.cursorProperty().bind(chessGame.whitePlayer.cursor)
            }

        }
        if (value is Action) {
            imageView.cursor = Cursor.HAND
            imageView.onMouseClicked = EventHandler { event ->
                BeanContext.getBean(GameBoardController::class.java).onActionBlockClick(event)
            }

        }
        gridPane.children.add(imageView)

        return value
    }

    override fun putAll(from: Map<out Pair<Int, Int>, T>) {
        from.forEach {
            put(it.key, it.value)
        }
    }

    override fun remove(key: Pair<Int, Int>): T? {
        val result = get(key)
        if (result != null)
            gridPane.children.removeIf { it.id == cellToId(result as Cell) }
        return null
    }


}