package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.StageInitializer
import git.dimitrikvirik.chessgamedesktop.core.Controller
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.stage.Stage
import org.springframework.stereotype.Service
import java.util.*

@Service
class AlertController : Controller() {

    @FXML
    lateinit var message: Label

    lateinit var stage: Stage

    fun show(text: String) {
        stage = Stage()
        stage.scene = sceneContextHolder.getSceneContext("alert-message")?.scene
        stage.title = "Alert"
        val image = Image(
            Objects.requireNonNull(
                StageInitializer::class.java.getResourceAsStream("/img/logo.png")
            )
        )
        stage.icons.add(image)
        stage.isResizable = false
        message.text = text
        stage.show()
    }


    @FXML
    fun close(actionEvent: ActionEvent) {
        stage.close()
    }
}