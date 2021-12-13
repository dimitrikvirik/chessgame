package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.springframework.stereotype.Service

@Service
class WelcomeMenuController(
    val alertController: AlertController
) : Controller() {


    @FXML
    lateinit var password: PasswordField

    @FXML
    lateinit var email: TextField

    @FXML
    fun onRegistration(actionEvent: ActionEvent) {

        alertController.show("Hi")
        sceneContextHolder.switchScene("registration-menu")
    }

    @FXML
    fun onPasswordReset(actionEvent: ActionEvent) {
        sceneContextHolder.switchScene("passwordreset-menu")
    }

    @FXML
    fun onLogin(actionEvent: ActionEvent) {

    }

}