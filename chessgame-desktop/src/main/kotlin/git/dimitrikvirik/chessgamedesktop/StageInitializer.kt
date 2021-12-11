package git.dimitrikvirik.chessgamedesktop

import git.dimitrikvirik.chessgame.ChartApplication
import git.dimitrikvirik.chessgamedesktop.core.FXMLScanner
import git.dimitrikvirik.chessgamedesktop.core.SceneContextHolder
import git.dimitrikvirik.chessgamedesktop.service.ChessService
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

    @Value("\${application.width}")
    lateinit var prefWidth: String

    @Value("\${application.height}")
    lateinit var prefHeight: String

    override fun onApplicationEvent(event: ChartApplication.StageReadyEvent) {


        chessService.connect("2222")
        fxmlScanner.scan()


//        val chessMessage = ChessMessage(1 to 1, 2 to 2, "Black Player", Action.MOVE)
//
//        chessService.send( chessMessage)


        val sceneContext = sceneContextHolder.getSceneContext("game-board")
        sceneContextHolder.setStage(event.stage)
        val stage: Stage = event.stage
        val scene: Scene = sceneContext?.scene as Scene
        stage.scene = scene
        stage.title = applicationName
        stage.width = prefWidth.toDouble()
        stage.height = prefHeight.toDouble()
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
