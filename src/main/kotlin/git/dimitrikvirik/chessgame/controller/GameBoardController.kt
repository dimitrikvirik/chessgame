package git.dimitrikvirik.chessgame.controller

import git.dimitrikvirik.chessgame.core.Controller
import git.dimitrikvirik.chessgame.model.game.*
import git.dimitrikvirik.chessgame.service.ChessService
import javafx.fxml.FXML
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class GameBoardController : Controller() {

    @FXML
    lateinit var gridPanel: GridPane

    @Autowired
    lateinit var chessService: ChessService

    lateinit var chessGame: ChessGame

    @FXML
    fun initialize() {
        chessGame = chessService.chessGame

        //Options
        gridPanel.prefHeight = 600.0
        gridPanel.prefWidth = 600.0


        for (i in 0..7) {
            for (j in 0..7) {
                chessGame.chessBoard.squareLayer[i to j] = Cell(
                    i, j,
                    if ((i + j) % 2 == 0) SquareType.WHITE else SquareType.BLACK
                )
                chessGame.chessBoard.figureLayer[i to j] = Cell(i, j, ChessFigure.getByNumber(i, j))
            }
        }


        chessGame.chessBoard.drawSquareLayer(gridPanel)
        chessGame.chessBoard.drawFigureLayer(gridPanel)

    }


}
