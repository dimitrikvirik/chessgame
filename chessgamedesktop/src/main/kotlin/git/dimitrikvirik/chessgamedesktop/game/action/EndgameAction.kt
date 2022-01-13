package git.dimitrikvirik.chessgamedesktop.game.action

import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractAction
import git.dimitrikvirik.chessgamedesktop.core.model.AbstractFigure
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.game.figure.ChessFigure
import git.dimitrikvirik.chessgamedesktop.game.figure.model.ChessFigureColor
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Font

class EndgameAction(coordination: Coordination, override val type: String) :
    AbstractAction(coordination, Coordination(0, 0, 0), "Endgame") {
    override fun run(figure: AbstractFigure) {

        val chessFigure = figure as ChessFigure
        val firstWin = figure.color == ChessFigureColor.WHITE
        chessFigure.chessGame.end(firstWin)

        val winner = chessFigure.chessGame.winnerChessPlayer!!.userId
        val winnerText = Label("GG! $winner has won!")
        winnerText.font = Font.font(40.0)
        winnerText.viewOrder = -20.0
        winnerText.maxWidth = Double.MAX_VALUE;
        AnchorPane.setLeftAnchor(winnerText, 0.0);
        AnchorPane.setRightAnchor(winnerText, 0.0);
        AnchorPane.setTopAnchor(winnerText, 0.0);

        winnerText.alignment = Pos.CENTER;
        val pane = BeanContext.getBean(GameBoardController::class.java).pane
        pane.children.add(winnerText)

    }


}