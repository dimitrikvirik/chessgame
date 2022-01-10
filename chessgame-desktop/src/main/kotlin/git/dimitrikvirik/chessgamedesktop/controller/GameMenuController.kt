package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import org.springframework.stereotype.Service

@Service
class GameMenuController(
    val chessService: ChessService,
    val gameBoardController: GameBoardController,
) : Controller() {


    @FXML
    lateinit var pane: Pane

    @FXML
    lateinit var gameId: TextField

    @FXML
    lateinit var filename: TextField


    @FXML
    fun createGame(mouseEvent: MouseEvent) {
        chessService.create()
        gameId.text = chessService.game.id!!
        println("Created game")
    }

    @FXML
    fun joinGame(mouseEvent: MouseEvent) {
        chessService.connect(gameId.text)
        sceneContextHolder.switchScene("game-board")
    }

    @FXML
    fun watchGame(mouseEvent: MouseEvent) {
        sceneContextHolder.switchScene("game-board")
        chessService.read(filename.text + ".csv")

    }
}