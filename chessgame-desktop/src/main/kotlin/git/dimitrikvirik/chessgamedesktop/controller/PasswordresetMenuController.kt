package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import javafx.event.ActionEvent
import javafx.fxml.FXML
import org.springframework.stereotype.Service

@Service
class PasswordResetMenuController: Controller() {

    @FXML
    fun onGoBack(actionEvent: ActionEvent) {
        sceneContextHolder.switchScene("welcome-menu")
    }
}