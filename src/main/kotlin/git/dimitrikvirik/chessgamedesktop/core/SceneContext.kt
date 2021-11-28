package git.dimitrikvirik.chessgamedesktop.core

import javafx.scene.Parent
import javafx.scene.Scene
import lombok.Getter

@Getter
data class SceneContext(val name: String, val parent: Parent?) {
    val scene: Scene = Scene(parent)

}