package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.core.model.AbstractAction
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.scene.Cursor

abstract class SpecialAction(coordination: Coordination, prefix: String, override val type: String) :
    AbstractAction(coordination, Coordination(0, 0, 0), prefix) {
    override fun run(figure: AbstractFigure) {}

    override fun hover(): ObservableValue<Cursor> {
        return SimpleObjectProperty(Cursor.DEFAULT)
    }

}