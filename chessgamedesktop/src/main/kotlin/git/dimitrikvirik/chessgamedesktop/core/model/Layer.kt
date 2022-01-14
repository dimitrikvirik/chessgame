package git.dimitrikvirik.chessgamedesktop.core.model

import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

class Layer(
    val gridPane: GridPane,
    private val override: Boolean = true,
    private val zIndex: Int
) : MutableMap<Pair<Int, Int>, GameObject> {
    private val logger = LoggerFactory.getLogger(Layer::class.java)


    private val container: MutableMap<Pair<Int, Int>, GameObject> = mutableMapOf()

    private fun getNodes(): List<ImageView> {
        return gridPane.children.filterIsInstance<ImageView>().filter {
            it.id.endsWith(zIndex.toString())
        }
    }

    private fun imageviewToObject(imageView: ImageView): GameObject {
        try {
            val charArray = StringUtils.right(imageView.id, 3).toCharArray()
            val x = charArray[0].toString().toInt()
            val y = charArray[1].toString().toInt()
            return container[x to y]!!
        }catch (e: NullPointerException){
            e.printStackTrace()
            throw Exception()
        }
    }

    private fun imageviewToObject(imageViews: List<ImageView>): List<GameObject> {
        return imageViews.map {
            imageviewToObject(it)
        }

    }

    private fun getLayer(): Map<Pair<Int, Int>, GameObject> {
        return imageviewToObject(getNodes()).associateBy { it.cord.pair }
    }

    private fun getAll(): MutableMap<Pair<Int, Int>, GameObject> {
        return getLayer().toMutableMap()
    }

    private fun objectToImageView(gameObject: GameObject): ImageView {
        try {
            val imageView = ImageView(gameObject.assert)

            //TODO remove
            imageView.fitWidth = gridPane.prefWidth / 8
            imageView.fitHeight = gridPane.prefHeight / 8

            imageView.viewOrder = gameObject.cord.z.toDouble()
            imageView.id = gameObject.prefix + gameObject.cord
            GridPane.setColumnIndex(imageView, gameObject.cord.x)
            GridPane.setRowIndex(imageView, gameObject.cord.y)
            imageView.onMouseClicked = gameObject.click()
            imageView.cursorProperty().bind(gameObject.hover())
            return imageView
        } catch (e: IllegalArgumentException) {
            println()
            throw  IllegalArgumentException()
        }
    }


    override val entries: MutableSet<MutableMap.MutableEntry<Pair<Int, Int>, GameObject>>
        get() = getAll().entries

    override val keys: MutableSet<Pair<Int, Int>>
        get() = getAll().keys
    override val size: Int
        get() = getAll().size
    override val values: MutableCollection<GameObject>
        get() = getAll().values


    override fun containsKey(key: Pair<Int, Int>): Boolean {
        return getAll().containsKey(key)
    }

    override fun containsValue(value: GameObject): Boolean {
        return getAll().containsValue(value)
    }

    override fun isEmpty(): Boolean {
        return getAll().isEmpty()
    }

    override fun get(key: Pair<Int, Int>): GameObject? {
        return getAll()[key]
    }


    override fun clear() {

        getAll().keys.forEach {
            remove(it)
        }
    }

    override fun put(key: Pair<Int, Int>, value: GameObject): GameObject {
        if (containsKey(key) || containsValue(value)) {
            throw IllegalArgumentException("Can't override existed gameObject")
        }
        val imageView = objectToImageView(value)
        container[key] = value
        gridPane.children.add(imageView)
        return value
    }


    override fun putAll(from: Map<out Pair<Int, Int>, GameObject>) {
        from.forEach {
            put(it.key, it.value)
        }
    }

    override fun remove(key: Pair<Int, Int>): GameObject? {
        val result = get(key)
        if (result != null) {
            if (!gridPane.children.removeIf { it.id != null && it.id.endsWith(Coordination(key, zIndex).toString()) }) {
                throw IllegalArgumentException("Element with key $key not been removed from gridpane!")
            }
            if (!container.remove(key, result)) {
                throw IllegalArgumentException("Element with key $key not been removed from container!")
            }
        }
        return result
    }


}

