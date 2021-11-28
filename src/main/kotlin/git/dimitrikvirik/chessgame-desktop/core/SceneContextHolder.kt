package git.dimitrikvirik.chessgame.core

import javafx.scene.Parent
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class SceneContextHolder {
    private val sceneContextList: MutableMap<String, SceneContext> = HashMap()
    private var stage: Stage? = null
    fun createScene(name: String, parent: Parent?): SceneContext {
        val sceneContext = SceneContext(name, parent)
        sceneContextList[name] = sceneContext
        return sceneContext
    }

    fun getSceneContext(name: String): SceneContext? {
        return sceneContextList[name]
    }

    fun switchScene(name: String) {
        stage!!.scene = getSceneContext(name)?.scene
    }

    fun setStage(stage: Stage?) {
        this.stage = stage
    }
}
