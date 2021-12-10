package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureVirtual
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import java.lang.NullPointerException

class Layer<T : Cell>(private val gridPane: GridPane, private val prefix: Char) :
    MutableMap<Pair<Int, Int>, T> {


    private val container = mutableListOf<T>()

    private fun getNode(idPrefix: String): ImageView? {
        return gridPane.children.filterIsInstance<ImageView>().firstOrNull {
            it.id.startsWith(idPrefix)
        }
    }


    private fun getNodeByCord(x: Int, y: Int): ImageView? {


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
        val x = Character.getNumericValue(it[2])
        val y = Character.getNumericValue(it[3])
        val list = container.filter {
            it.cord.first == x && it.cord.second == y
        }
        if (list.isEmpty()) {
            throw  NullPointerException()
        }
        if (list.size > 1) {
            throw Exception()
        }

        return list[0]
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
            is ChessFigure -> {
                result.append('F')
            }
            is Square -> result.append('S')
            is Shah -> result.append('H')
        }
        when (cell) {
            is Action -> result.append(cell.action.prefix)
            is ChessFigure -> result.append(cell.chessFigureType.prefix)
            is Square -> result.append(cell.square.prefix)
            is Shah -> result.append(cell.prefix)
        }
        result.append(cell.cord.first)
        result.append(cell.cord.second)
        if (cell is ChessFigure) {
            result.append(cell.color.prefix)
        }
        return result.toString()
    }

    private fun cellToImageView(cell: Cell): ImageView {


        val resource = "/assert/${cell.resource}"
        val imageView = ImageView(resource)
        imageView.fitWidth = gridPane.prefWidth / 8
        imageView.fitHeight = gridPane.prefHeight / 8
        imageView.viewOrder = cell.order.toDouble()
        imageView.id = cellToId(cell)
        GridPane.setConstraints(imageView, cell.cord.first, cell.cord.second)
        return imageView
    }


    private fun getLayer(type: Char = ' '): Map<Pair<Int, Int>, T> {
        return imageViewToCell(getNodes(type.toString().replace(" ", ""))).associateBy {
            it.cord
        }
    }

    private fun getAll(): MutableMap<Pair<Int, Int>, T> {
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
        val nodeByCord = getNodeByCord(key.first, key.second)
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

        val existed = get(key)
        if (existed != null) {
            remove(key)
        }

        var imageView = cellToImageView(value)
        if (value is ChessFigure) {

            val charArray = imageView.id.toCharArray()
            charArray[0] = 'V'
            imageView.id = charArray.joinToString("")
            imageView.image = Image("/assert/virtual.png")

            gridPane.children.add(imageView)

            imageView = cellToImageView(value)


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
        container.add(value)
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
        if (result != null) {
            gridPane.children.removeIf { it.id == cellToId(result as Cell) }
            container.removeIf { it.cord == key }

        }
        return null
    }


}