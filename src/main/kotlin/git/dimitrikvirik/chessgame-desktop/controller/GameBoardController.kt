package git.dimitrikvirik.chessgame.controller

import git.dimitrikvirik.chessgame.core.Controller
import git.dimitrikvirik.chessgame.model.game.*
import git.dimitrikvirik.chessgame.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgame.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgame.model.game.figure.ChessKing
import git.dimitrikvirik.chessgame.service.ChessService
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class GameBoardController : Controller() {


    lateinit var chessGame: ChessGame

    @Autowired
    lateinit var chessService: ChessService

    @FXML
    lateinit var gridPanel: GridPane

    @FXML
    lateinit var blackPlayerUsername: Label


    @FXML
    lateinit var whitePlayerUsername: Label

    lateinit var chessBoard: ChessBoard

    var selectedFigure: ChessFigure? = null

    @Value("\${application.width}")
    lateinit var prefWith: String

    @Value("\${application.height}")
    lateinit var prefHeight: String

    @FXML
    fun initialize() {
        chessGame = chessService.chessGame
        whitePlayerUsername.text = chessGame.whitePlayer.user.username
        blackPlayerUsername.text = chessGame.blackPlayer.user.username

        chessBoard = chessGame.chessBoard


        gridPanel.prefHeight = prefWith.toDouble() - 200
        gridPanel.prefWidth = prefHeight.toDouble() - 200
        whitePlayerUsername.layoutY = prefHeight.toDouble() - 150
        chessBoard.gridPane = gridPanel
        chessBoard.chessGame = chessGame

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
        val clickedPlayer: ChessPlayer = if (figure?.color == ChessFigureColor.WHITE) {
            chessGame.whitePlayer
        } else {
            chessGame.blackPlayer
        }
        if (clickedPlayer.canMove) {
            chessBoard.clearActionLayer()
            selectedFigure = figure
            if (figure != null) {
                val movableBlocks = chessBoard.fixBlocks(figure.getMovableBlocks())

                movableBlocks.forEach {
                    chessBoard.actionLayer[it.first to it.second] = Action.MOVE
                }

                val killableBlocks = figure.getKillableBlocks()

                killableBlocks.forEach {
                    chessBoard.actionLayer[it.first to it.second] = Action.KILL
                }

                chessBoard.drawActionLayer()
            }
        }
    }

    fun onMoveBlockClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)

        selectedFigure?.move(xIndex, yIndex)
        selectedFigure?.clearKillableBlocks()
        chessGame.goNextPlayer()
        chessGame.checkShah()
    }

    fun onKillBlockClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)


        selectedFigure?.kill(xIndex, yIndex)
        selectedFigure?.clearKillableBlocks()
        chessGame.goNextPlayer()
        chessGame.checkShah()

    }

}
