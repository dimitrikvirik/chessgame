package git.dimitrikvirik.chessgamedesktop

import git.dimitrikvirik.chessgame.ChartApplication
import git.dimitrikvirik.chessgamedesktop.core.FXMLScanner
import git.dimitrikvirik.chessgamedesktop.core.SceneContextHolder
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class StageInitializer : ApplicationListener<ChartApplication.StageReadyEvent> {

    @Autowired
    lateinit var fxmlScanner: FXMLScanner

    @Autowired
    lateinit var sceneContextHolder: SceneContextHolder



    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @Value("\${application.resizible}")
    lateinit var resizible: String

    @Autowired
    lateinit var chessService: ChessService

    override fun onApplicationEvent(event: ChartApplication.StageReadyEvent) {


        fxmlScanner.scan()

        val sceneContext = sceneContextHolder.getSceneContext("game-menu")
        sceneContextHolder.setStage(event.stage)
        val stage: Stage = event.stage
        val scene: Scene = sceneContext?.scene as Scene
        stage.scene = scene
        stage.title = applicationName

        val image = Image(
            Objects.requireNonNull(
                StageInitializer::class.java.getResourceAsStream("/img/logo.png")
            )
        )
        stage.icons.add(image)
        stage.isResizable = true

        stage.show()


    }
}
