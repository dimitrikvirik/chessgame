package git.dimitrikvirik.chessgame.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
abstract class Controller {

    @Autowired
    lateinit var sceneContextHolder: SceneContextHolder

    lateinit var  sceneContextObj: SceneContext


    fun setSceneContext(sceneContext: SceneContext?) {
        if (sceneContext != null) {
            this.sceneContextObj = sceneContext
        }
    }
}
