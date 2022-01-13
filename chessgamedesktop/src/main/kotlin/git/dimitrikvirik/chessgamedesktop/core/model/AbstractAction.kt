package git.dimitrikvirik.chessgamedesktop.core.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.scene.Cursor

abstract class AbstractAction(val coordination: Coordination, val targetCoordination: Coordination, prefix: String) :
    GameObject(coordination, "A$prefix") {

    abstract val type: String

    abstract fun run(figure: AbstractFigure)


    override fun hover(): ObservableValue<Cursor> {
        return SimpleObjectProperty(Cursor.HAND)
    }

}