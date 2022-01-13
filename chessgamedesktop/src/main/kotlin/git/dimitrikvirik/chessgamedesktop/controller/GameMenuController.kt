package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import java.net.ConnectException
import java.util.concurrent.ExecutionException

@Service
class GameMenuController(
    val chessService: ChessService,
    val alertController: AlertController,
) : Controller() {


    @FXML
    lateinit var pane: Pane

    @FXML
    lateinit var gameId: TextField

    @FXML
    lateinit var filename: TextField


    @FXML
    lateinit var username: TextField

    @FXML
    lateinit var server: TextField


    @FXML
    fun createGame(mouseEvent: MouseEvent) {
        try {
            chessService.create(server.text)
            gameId.text = chessService.game.id!!
            println("Created game")
        } catch (e: ResourceAccessException) {
            alertController.show("Cannot connect to ${server.text}")
        }
    }

    @FXML
    fun joinGame(mouseEvent: MouseEvent) {
        try {
            chessService.connect(gameId.text, username.text, server.text)
            sceneContextHolder.switchScene("game-board")
        } catch (e: ExecutionException) {
            alertController.show("Cannot connect to ${server.text}")
        }
        catch (e: HttpClientErrorException){
            alertController.show("Not found game with id ${gameId.text}")
        }
    }

    @FXML
    fun watchGame(mouseEvent: MouseEvent) {
        sceneContextHolder.switchScene("game-board")
        chessService.read(filename.text + ".csv")

    }
}