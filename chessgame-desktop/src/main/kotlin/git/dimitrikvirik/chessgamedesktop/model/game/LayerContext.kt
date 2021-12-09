package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureVirtual
import javafx.scene.layout.GridPane
import org.springframework.stereotype.Component

@Component
class LayerContext {

    lateinit var squareLayer: Layer<Square>
    lateinit var figureLayer: Layer<ChessFigure>
    lateinit var actionLayer: Layer<Action>
    lateinit var virtualLayer: Layer<ChessFigureVirtual>

    fun init(gridPanel: GridPane) {
        squareLayer = Layer(gridPanel, 'S', false)
        figureLayer = Layer(gridPanel, 'F', false)
        actionLayer = Layer(gridPanel, 'A', false)
        virtualLayer = Layer(gridPanel, 'V', true)
    }
}