package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import git.dimitrikvirik.chessgamedesktop.model.game.*
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureUtil
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import git.dimitrikvirik.chessgamedesktop.service.ChessService
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GameBoardController(
    val chessGame: ChessGame,
    val chessService: ChessService,
    val layerContext: LayerContext,
) : Controller() {


    @FXML
    lateinit var gridPanel: GridPane

    @FXML
    lateinit var blackPlayerUsername: Label

    @FXML
    lateinit var whitePlayerUsername: Label

    @FXML
    lateinit var blackPlayerPhoto: ImageView

    @FXML
    lateinit var whitePlayerPhoto: ImageView

    @FXML
    lateinit var pane: AnchorPane


    var selectedFigure: ChessFigure? = null

    @Value("\${application.width}")
    lateinit var prefWidth: String

    @Value("\${application.height}")
    lateinit var prefHeight: String

    lateinit var figureLayer: Layer<ChessFigure>
    lateinit var actionLayer: Layer<Action>


    @FXML
    fun initialize() {
        whitePlayerUsername.text = chessGame.whitePlayer.user.username
        blackPlayerUsername.text = chessGame.blackPlayer.user.username
        whitePlayerPhoto.image = Image("/img/default_profile.jpg")
        blackPlayerPhoto.image = Image("/img/default_profile.jpg")

        gridPanel.prefHeight = prefHeight.toDouble() - 200
        gridPanel.prefWidth = prefWidth.toDouble() - 200
        gridPanel.layoutY = 20.0
        gridPanel.layoutX = 100.0
        whitePlayerUsername.layoutY = prefHeight.toDouble() - 180
        layerContext.init(gridPanel)

        val squareLayer = layerContext.squareLayer
        figureLayer = layerContext.figureLayer
        actionLayer = layerContext.actionLayer
        val alphabets = ('A'..'Z').toMutableList()
        for (i in 0..7) {
            for (j in 0..7) {
                val squareType = if ((i + j) % 2 == 0) SquareType.WHITE else SquareType.BLACK
                squareLayer[i + 1 to j] = Square(squareType, i + 1 to j)
                val chessFigure = ChessFigureUtil.getByNumber(i + 1, j)
                if (chessFigure != null) figureLayer[i + 1 to j] = chessFigure
            }
            val label = Label((i + 1).toString())
            label.font = Font(50.0)
            GridPane.setConstraints(label, 0, i)
            gridPanel.children.add(label)

            val labelAlphabet = Label(alphabets[i].toString())
            labelAlphabet.font = Font(50.0)
            GridPane.setConstraints(labelAlphabet, i + 1, 9)
            gridPanel.children.add(labelAlphabet)
        }


    }


    fun onFigureClick(event: MouseEvent) {
        val figure = figureLayer[event.getCord()]

        if (chessGame.currentPlayer.chessFigureColor == figure?.color) {
            actionLayer.clear()
            selectedFigure = figure
            val movableBlocks = figure.getMovableBlocks()

            movableBlocks.forEach {
                actionLayer[it.first to it.second] = Action(ActionType.MOVE, it)
            }
            val killableBlocks = figure.getKillableBlocks()
            killableBlocks.forEach {
                actionLayer[it.first to it.second] = Action(ActionType.KILL, it)
            }

            val swapBlocks = figure.getSwapBlocks()
            swapBlocks.forEach {
                actionLayer[it.first to it.second] = Action(ActionType.SWAP, it)
            }

        }
    }


    fun onActionBlockClick(event: MouseEvent) {


        val cord = event.getCord()
        val action = layerContext.actionLayer[cord]?.action
        if (action == ActionType.MOVE) {
            chessService.send(ChessMessage(selectedFigure!!.cord, cord, ActionType.MOVE, chessGame.currentStep))
        } else if (action == ActionType.KILL) {
            chessService.send(ChessMessage(selectedFigure!!.cord, cord, ActionType.KILL, chessGame.currentStep))
        } else if (action == ActionType.SWAP) {
            chessService.send(ChessMessage(selectedFigure!!.cord, cord, ActionType.SWAP, chessGame.currentStep))
        }
    }


    fun endgame() {
        val winner = chessGame.winnerPlayer!!.user.username
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

fun MouseEvent.getCord(): Pair<Int, Int> {
    val img = source as ImageView
    val xIndex = GridPane.getColumnIndex(img)
    val yIndex = GridPane.getRowIndex(img)
    return xIndex to yIndex
}
