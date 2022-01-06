package git.dimitrikvirik.chessgamedesktop.core

import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import git.dimitrikvirik.chessgamedesktop.core.model.Layer
import git.dimitrikvirik.chessgamedesktop.core.model.ObjectIndex
import git.dimitrikvirik.chessgamedesktop.core.model.Player
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.layout.GridPane


abstract class GameEngine {

    var currentStep: Int = 0
    var ended = false
    var readMode = false


    lateinit var firstPlayer: Player
    lateinit var secondPlayer: Player
    var currentPlayer = SimpleObjectProperty<Player>()


    lateinit var figureLayer: Layer
    lateinit var actionLayer: Layer
    lateinit var squareLayer: Layer
    lateinit var specialActionLayer: Layer

    var winnerPlayer: Player? = null


    fun start(firstPlayer: Player, secondPlayer: Player, gridPane: GridPane) {
        this.firstPlayer = firstPlayer
        this.secondPlayer = secondPlayer
        currentPlayer.set(firstPlayer)
        figureLayer = Layer(gridPane, true, ObjectIndex.FIGURE)
        actionLayer = Layer(gridPane, false, ObjectIndex.ACTION)
        squareLayer = Layer(gridPane, false, ObjectIndex.SQUARE)
        specialActionLayer = Layer(gridPane, false, ObjectIndex.S_ACTION)
    }

    fun end(firstWin: Boolean) {
        winnerPlayer = if (firstWin) {
            firstPlayer
        } else secondPlayer
        ended = true
    }


    abstract fun handle(gameMessage: GameMessage)


}