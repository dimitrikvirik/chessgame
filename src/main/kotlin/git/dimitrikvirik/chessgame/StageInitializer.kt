package git.dimitrikvirik.chessgame

import git.dimitrikvirik.chessgame.core.FXMLScanner
import git.dimitrikvirik.chessgame.core.SceneContextHolder
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class StageInitializer : ApplicationListener<ChartApplication.StageReadyEvent> {

    @Autowired
    lateinit var fxmlScanner: FXMLScanner

    @Autowired
    lateinit var sceneContextHolder: SceneContextHolder

    override fun onApplicationEvent(event: ChartApplication.StageReadyEvent) {
        fxmlScanner.scan()

        val sceneContext = sceneContextHolder.getSceneContext("game-board")
        sceneContextHolder.setStage(event.stage)
        val stage: Stage = event.stage
        val scene: Scene = sceneContext?.scene as Scene
        stage.scene = scene
        stage.title = "ChessGame"
//        val image = Image(
//            Objects.requireNonNull(
//                StageInitializer::class.java.getResourceAsStream("/desktopApp/img/app-icon.png")
//            )
//        )
//        stage.icons.add(image)
        stage.isResizable = false
        stage.show()
    }
}
