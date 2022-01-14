package git.dimitrikvirik.chessgamedesktop.controller

import git.dimitrikvirik.chessgamedesktop.core.Controller
import git.dimitrikvirik.chessgamedesktop.core.model.ChessPlayer
import git.dimitrikvirik.chessgamedesktop.core.model.Layer
import git.dimitrikvirik.chessgamedesktop.core.model.PlayerMessage
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.game.figure.FigureBuilder
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.game.square.BlackSquare
import git.dimitrikvirik.chessgamedesktop.game.square.WhiteSquare
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GameBoardController(
    val chessGame: ChessGame
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


    @Value("\${application.width}")
    lateinit var prefWidth: String

    @Value("\${application.height}")
    lateinit var prefHeight: String

    lateinit var figureLayer: Layer
    lateinit var actionLayer: Layer

    lateinit var whitePlayer: ChessPlayer
    lateinit var blackPlayer: ChessPlayer


    fun updateUserText() {
        whitePlayerUsername.text = whitePlayer.userId
        blackPlayerUsername.text = blackPlayer.userId
    }


    fun setUser(playerMessage: PlayerMessage) {
        whitePlayer = if (!playerMessage.whiteConnected) {
            val msg = if (playerMessage.whitePlayerName != null) {
                "${playerMessage.whitePlayerName} disconnected"
            } else "Not Connected"

            ChessPlayer(msg, ChessFigureColor.WHITE)
        } else {
            ChessPlayer(playerMessage.whitePlayerName!!, ChessFigureColor.WHITE)
        }
        blackPlayer = if (!playerMessage.blackConnected) {
            val msg = if (playerMessage.blackPlayerName != null) {
                "${playerMessage.blackPlayerName} disconnected"
            } else "Not Connected"

            ChessPlayer(msg, ChessFigureColor.BLACK)
        } else ChessPlayer(playerMessage.blackPlayerName!!, ChessFigureColor.BLACK)
        if (chessGame.chessService.username == whitePlayer.userId) {
            chessGame.joinedChessPlayer = whitePlayer
        } else {
            chessGame.joinedChessPlayer = blackPlayer
        }
        chessGame.firstChessPlayer = whitePlayer
        chessGame.secondChessPlayer = blackPlayer
        if (chessGame.joinedChessPlayer.color == ChessFigureColor.BLACK) {
            gridPanel.rotate = 180.0
        }
    }


    fun loadGame() {

        chessGame.setPane(gridPanel)


        figureLayer = chessGame.figureLayer
        actionLayer = chessGame.actionLayer


        whitePlayerPhoto.image = Image("/img/default_profile.jpg")
        blackPlayerPhoto.image = Image("/img/default_profile.jpg")

        gridPanel.prefHeight = prefHeight.toDouble() - 200
        gridPanel.prefWidth = prefWidth.toDouble() - 200
        gridPanel.layoutY = 20.0
        gridPanel.layoutX = 100.0
        whitePlayerUsername.layoutY = prefHeight.toDouble() - 180


        val squareLayer = chessGame.squareLayer
        figureLayer = chessGame.figureLayer
        actionLayer = chessGame.actionLayer
        val alphabets = ('A'..'Z').toMutableList()
        for (i in 0..7) {
            for (j in 0..7) {
                val pair = i + 1 to j
                val square = if ((i + j) % 2 == 0) {
                    WhiteSquare(pair)
                } else BlackSquare(pair)
                squareLayer[i + 1 to j] = square
                val chessFigure = FigureBuilder.getByNumber(i + 1, j)
                if (chessFigure != null) figureLayer[i + 1 to j] = chessFigure
            }
            val label = Label((7 - i + 1).toString())
            label.font = Font(50.0)
            GridPane.setConstraints(label, 0, i)
            if (chessGame.joinedChessPlayer.color == ChessFigureColor.BLACK)
                label.rotate = 180.0
            gridPanel.children.add(label)

            val labelAlphabet = Label(alphabets[i].toString())
            if (chessGame.joinedChessPlayer.color == ChessFigureColor.BLACK)
                labelAlphabet.rotate = 180.0
            labelAlphabet.font = Font(50.0)
            GridPane.setConstraints(labelAlphabet, i + 1, 9)
            gridPanel.children.add(labelAlphabet)


        }
    }

}
