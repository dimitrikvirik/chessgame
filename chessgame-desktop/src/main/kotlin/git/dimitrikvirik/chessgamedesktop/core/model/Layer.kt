package git.dimitrikvirik.chessgamedesktop.core.model

import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import org.slf4j.LoggerFactory

class Layer(
     val gridPane: GridPane,
    private val override: Boolean = true,
    private val zIndex: Int
) : MutableMap<Pair<Int, Int>, GameObject> {
    private val logger = LoggerFactory.getLogger(Layer::class.java)


    private val objectMap: MutableMap<Coordination, GameObject> = mutableMapOf()


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
        get() = objectMap.entries.associate {
            (it.key.x to it.key.y) to it.value
        }.toMutableMap().entries

    override val keys: MutableSet<Pair<Int, Int>>
        get() = objectMap.map { it.key.x to it.key.y }.toMutableSet()
    override val size: Int
        get() = objectMap.size
    override val values: MutableCollection<GameObject>
        get() = objectMap.values


    override fun containsKey(key: Pair<Int, Int>): Boolean {
        return objectMap.any { it.key.x == key.first && it.key.y == key.second }
    }

    override fun containsValue(value: GameObject): Boolean {
        return objectMap.containsValue(value)
    }

    override fun isEmpty(): Boolean {
        return objectMap.isEmpty()
    }

    override fun get(key: Pair<Int, Int>): GameObject? {
        return objectMap[Coordination(key, zIndex)]
    }


    override fun clear() {
        objectMap.keys.forEach { key ->
            val gameObject = get(key.pair) ?: throw IllegalArgumentException("gameObject not found")
            gridPane.children.removeIf { it.id == gameObject.prefix + gameObject.cord }
        }
        objectMap.clear()
    }


    override fun put(key: Pair<Int, Int>, value: GameObject): GameObject {

        if (containsValue(value)) {
            if (!override) {
                throw IllegalArgumentException("Can't override existed gameObject")
            }
            val gameObject = values.first { it == value }
            gridPane.children.removeIf { it.id == gameObject.prefix + gameObject.cord }
            values.removeIf { it == gameObject }
        }
        if(containsKey(key)){
            if (!override) {
                throw IllegalArgumentException("Can't override existed gameObject")
            }
            remove(key)
        }

        val imageView = objectToImageView(value)
        objectMap[Coordination(key, zIndex)] = value
        gridPane.children.add(imageView)
        return value
    }


    override fun putAll(from: Map<out Pair<Int, Int>, GameObject>) {
        from.forEach {
            put(it.key, it.value)
        }
    }

    override fun remove(key: Pair<Int, Int>): GameObject {
        val gameObject = get(key) ?: throw IllegalArgumentException("gameObject not found")
        gridPane.children.removeIf { it.id == gameObject.prefix + gameObject.cord }
        objectMap.remove(Coordination(key, zIndex))
        return gameObject
    }


}

