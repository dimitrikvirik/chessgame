package git.dimitrikvirik.chessgame.model.game

import git.dimitrikvirik.chessgame.model.domain.User
import git.dimitrikvirik.chessgame.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgame.model.game.figure.ChessKing
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Cursor


class ChessPlayer(
    val chessFigureColor: ChessFigureColor,
    val user: User
) {
    var canMove: Boolean = false
    val cursor: ObjectProperty<Cursor> = SimpleObjectProperty(this, "cursor")
    lateinit var king: ChessKing

}