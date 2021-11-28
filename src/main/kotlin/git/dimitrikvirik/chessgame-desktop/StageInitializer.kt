package git.dimitrikvirik.chessgame

import git.dimitrikvirik.chessgame.core.FXMLScanner
import git.dimitrikvirik.chessgame.core.SceneContextHolder
import git.dimitrikvirik.chessgame.service.ChessService
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class StageInitializer : ApplicationListener<ChartApplication.StageReadyEvent> {

    @Autowired
    lateinit var fxmlScanner: FXMLScanner

    @Autowired
    lateinit var sceneContextHolder: SceneContextHolder

    @Autowired
    lateinit var chessService: ChessService

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @Value("\${application.resizible}")
    lateinit var resizible: String

    override fun onApplicationEvent(event: ChartApplication.StageReadyEvent) {
        fxmlScanner.scan()

        chessService.connect("wss://demo.piesocket.com/v3/channel_1?api_key=oCdCMcMPQpbvNjUIzqtvF1d2X2okWpDQj4AwARJuAgtjhzKxVEjQU6IdCjwm&notify_self")

        val sceneContext = sceneContextHolder.getSceneContext("game-board")
        sceneContextHolder.setStage(event.stage)
        val stage: Stage = event.stage
        val scene: Scene = sceneContext?.scene as Scene
        stage.scene = scene
        stage.title = applicationName
//        val image = Image(
//            Objects.requireNonNull(
//                StageInitializer::class.java.getResourceAsStream("/desktopApp/img/app-icon.png")
//            )
//        )
//        stage.icons.add(image)
        stage.isResizable = resizible.toBoolean()
        stage.show()
    }
}
