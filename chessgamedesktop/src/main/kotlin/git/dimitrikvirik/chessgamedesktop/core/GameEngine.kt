package git.dimitrikvirik.chessgamedesktop.core

import git.dimitrikvirik.chessgamedesktop.core.model.ChessPlayer
import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import git.dimitrikvirik.chessgamedesktop.core.model.Layer
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.layout.GridPane


abstract class GameEngine {

    var currentStep: Int = 0
    var ended = false
    var readMode = false


    lateinit var gameId: String
    var firstChessPlayer: ChessPlayer = ChessPlayer("Not Connected", ChessFigureColor.WHITE)
    var secondChessPlayer: ChessPlayer = ChessPlayer("Not Connected", ChessFigureColor.BLACK)
    var currentChessPlayer = SimpleObjectProperty<ChessPlayer>()
    lateinit var joinedChessPlayer: ChessPlayer


    lateinit var figureLayer: Layer
    lateinit var actionLayer: Layer
    lateinit var squareLayer: Layer
    lateinit var specialActionLayer: Layer

    var winnerChessPlayer: ChessPlayer? = null


    fun setPane(gridPane: GridPane) {
        figureLayer = Layer(gridPane, true, ObjectIndex.FIGURE)
        actionLayer = Layer(gridPane, false, ObjectIndex.ACTION)
        squareLayer = Layer(gridPane, false, ObjectIndex.SQUARE)
        specialActionLayer = Layer(gridPane, false, ObjectIndex.S_ACTION)
    }

    fun isInit(): Boolean {
        return this::joinedChessPlayer.isInitialized
    }

    fun end(firstWin: Boolean) {
        winnerChessPlayer = if (firstWin) {
            firstChessPlayer
        } else secondChessPlayer
        ended = true
    }


    abstract fun handle(gameMessage: GameMessage)


}