package git.dimitrikvirik.chessgamedesktop.core.model

import git.dimitrikvirik.chessgamedesktop.core.AssertLoader
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.input.MouseEvent


abstract class GameObject(open var cord: Coordination, val prefix: String) {
    val assert: String = BeanContext.getBean(AssertLoader::class.java).load(prefix)


    open fun click(): EventHandler<MouseEvent> {
        return EventHandler<MouseEvent> {

        }
    }
    open fun hover(): ObservableValue<Cursor> {
        return SimpleObjectProperty(Cursor.DEFAULT)
    }

}