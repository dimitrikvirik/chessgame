package git.dimitrikvirik.chessgame.controller

import git.dimitrikvirik.chessgame.core.Controller
import git.dimitrikvirik.chessgame.model.game.*
import git.dimitrikvirik.chessgame.service.ChessService
import javafx.fxml.FXML
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class GameBoardController : Controller() {

    @FXML
    lateinit var gridPanel: GridPane

    @Autowired
    lateinit var chessService: ChessService

    lateinit var chessGame: ChessGame

    lateinit var chessBoard: ChessBoard

    var selectedFigure: ChessFigure? = null

    @FXML
    fun initialize() {
        chessGame = chessService.chessGame
        chessBoard = chessGame.chessBoard

        //Options
        gridPanel.prefHeight = 600.0
        gridPanel.prefWidth = 600.0
        chessBoard.gridPane = gridPanel

        for (i in 0..7) {
            for (j in 0..7) {
                chessBoard.squareLayer[i to j] = if ((i + j) % 2 == 0) SquareType.WHITE else SquareType.BLACK
                chessBoard.figureLayer[i to j] = ChessFigure.getByNumber(i, j, chessGame.chessBoard)
            }
        }

        chessBoard.drawSquareLayer()
        chessBoard.drawFigureLayer()

    }

    fun onFigureClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)
        val figure = chessBoard.figureLayer[xIndex to yIndex]
        chessBoard.clearActionLayer()
        selectedFigure = figure
        if (figure != null) {
            val movableBlocks = figure.getMovableBlocks()
            movableBlocks.forEach {
                chessBoard.actionLayer[it.first to it.second] = Action.MOVE
            }

            val  killableBlocks = figure.getKillableBlocks()
            killableBlocks.forEach {
                chessBoard.actionLayer[it.first to it.second] = Action.KILL
            }

            chessBoard.drawActionLayer()

        }
    }

    fun onMoveBlockClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)
        selectedFigure?.move(xIndex, yIndex)

    }

}
