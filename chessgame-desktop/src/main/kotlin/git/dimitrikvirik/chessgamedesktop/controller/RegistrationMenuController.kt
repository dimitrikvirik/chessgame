package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import git.dimitrikvirik.chessgamedesktop.model.param.UserRegParam
import git.dimitrikvirik.chessgamedesktop.service.UserService
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.springframework.stereotype.Service

@Service
class RegistrationMenuController(
   val userService: UserService
) : Controller() {

    @FXML
    lateinit var password: PasswordField

    @FXML
    lateinit var username: TextField

    @FXML
    lateinit var email: TextField

    @FXML
    fun onLogin(actionEvent: ActionEvent) {
        sceneContextHolder.switchScene("welcome-menu")
    }

    @FXML
    fun onRegistrate(actionEvent: ActionEvent) {
        userService.registration(UserRegParam(
            username.text,
            email.text,
            password.text
        ))
    }

}