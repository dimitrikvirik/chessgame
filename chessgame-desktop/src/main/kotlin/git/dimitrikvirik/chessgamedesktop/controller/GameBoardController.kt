package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import git.dimitrikvirik.chessgamedesktop.model.game.*
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureUtil
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
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

    @Autowired
    lateinit var layerContext: LayerContext

    @FXML
    lateinit var gridPanel: GridPane

    @FXML
    lateinit var blackPlayerUsername: Label

    @FXML
    lateinit var whitePlayerUsername: Label

    @FXML
    lateinit var pane: AnchorPane


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
        gridPanel.prefHeight = prefWith.toDouble() - 200
        gridPanel.prefWidth = prefHeight.toDouble() - 200
        whitePlayerUsername.layoutY = prefHeight.toDouble() - 150
        layerContext.init(gridPanel)

        val squareLayer = layerContext.squareLayer
        val figureLayer = layerContext.figureLayer
        for (i in 0..7) {
            for (j in 0..7) {
                val squareType = if ((i + j) % 2 == 0) SquareType.WHITE else SquareType.BLACK
                squareLayer[i to j] = Square(squareType, i, j)
                val chessFigure = ChessFigureUtil.getByNumber(i, j)
                if (chessFigure != null) figureLayer[i to j] = chessFigure
            }
        }


    }


    fun onFigureClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)

        val figureLayer = layerContext.figureLayer
        val actionLayer = layerContext.actionLayer

        val figure = figureLayer[xIndex to yIndex]
        val clickedPlayer: ChessPlayer = if (figure?.color == ChessFigureColor.WHITE) {
            chessGame.whitePlayer
        } else {
            chessGame.blackPlayer
        }
        if (clickedPlayer.canMove) {

            actionLayer.clear()
            selectedFigure = figure
            if (figure != null) {
                val movableBlocks = figure.getMovableBlocks()

                movableBlocks.forEach {
                    actionLayer[it.first to it.second] = Action(ActionType.MOVE, it.first, it.second)
                }
                val killableBlocks = figure.getKillableBlocks()
                killableBlocks.forEach {
                    actionLayer[it.first to it.second] = Action(ActionType.KILL, it.first, it.second)
                }
            }
        }
    }


    fun onActionBlockClick(event: MouseEvent) {

    }


    fun onMoveBlockClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)

        selectedFigure?.move(xIndex, yIndex, chessService)
        selectedFigure?.clearKillableBlocks()

    }

    fun onKillBlockClick(event: MouseEvent) {
        val img = event.source as ImageView
        val xIndex = GridPane.getColumnIndex(img)
        val yIndex = GridPane.getRowIndex(img)


        selectedFigure?.kill(xIndex, yIndex, chessService)
        selectedFigure?.clearKillableBlocks()

    }

    fun endgame() {
        val winner = chessGame.currentPlayer.user.username
        val winnerText = Label("GG! $winner has won!")
        winnerText.font = Font.font(40.0)
        winnerText.viewOrder = 20.0
        winnerText.maxWidth = Double.MAX_VALUE;
        AnchorPane.setLeftAnchor(winnerText, 0.0);
        AnchorPane.setRightAnchor(winnerText, 0.0);
//        AnchorPane.setBottomAnchor(winnerText, 0.0);
//        AnchorPane.setTopAnchor(winnerText, 0.0);

        winnerText.alignment = Pos.CENTER;
        pane.children.add(winnerText)
    }
}
