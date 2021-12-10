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
    lateinit var prefWidth: String

    @Value("\${application.height}")
    lateinit var prefHeight: String

    lateinit var figureLayer: Layer<ChessFigure>
    lateinit var actionLayer: Layer<Action>

    @FXML
    fun initialize() {
        chessGame = chessService.chessGame
        whitePlayerUsername.text = chessGame.whitePlayer.user.username
        blackPlayerUsername.text = chessGame.blackPlayer.user.username
        gridPanel.prefHeight = prefHeight.toDouble() - 150
        gridPanel.prefWidth = prefWidth.toDouble() - 180
        whitePlayerUsername.layoutY = prefHeight.toDouble() - 100
        layerContext.init(gridPanel)

        val squareLayer = layerContext.squareLayer
        figureLayer = layerContext.figureLayer
        actionLayer = layerContext.actionLayer
        for (i in 0..7) {
            for (j in 0..7) {
                val squareType = if ((i + j) % 2 == 0) SquareType.WHITE else SquareType.BLACK
                squareLayer[i to j] = Square(squareType, i to j)
                val chessFigure = ChessFigureUtil.getByNumber(i, j)
                if (chessFigure != null) figureLayer[i to j] = chessFigure
            }
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
        }
    }


    fun onActionBlockClick(event: MouseEvent) {


        val cord = event.getCord()
        val action = layerContext.actionLayer[cord]?.action
        if (action == ActionType.MOVE) {
            chessService.send(ChessMessage(selectedFigure!!.cord, cord, ActionType.MOVE))
        } else if (action == ActionType.KILL) {
            chessService.send(ChessMessage(selectedFigure!!.cord, cord, ActionType.KILL))
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
