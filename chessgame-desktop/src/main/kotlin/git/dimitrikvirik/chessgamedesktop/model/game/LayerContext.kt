package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import javafx.scene.layout.GridPane
import org.springframework.stereotype.Component

@Component
class LayerContext {

    lateinit var squareLayer: Layer<Square>
    lateinit var figureLayer: Layer<ChessFigure>
    lateinit var actionLayer: Layer<Action>

    fun init(gridPanel: GridPane) {
        squareLayer = Layer(gridPanel, 'S')
        figureLayer = Layer(gridPanel, 'F')
        actionLayer = Layer(gridPanel, 'A')
    }
}