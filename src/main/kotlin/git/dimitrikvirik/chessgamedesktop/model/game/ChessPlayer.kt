package git.dimitrikvirik.chessgamedesktop.model.game

import git.dimitrikvirik.chessgamedesktop.model.domain.User
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessFigureColor
import git.dimitrikvirik.chessgamedesktop.model.game.figure.ChessKing
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Cursor
import java.io.Serializable


class ChessPlayer(
    val chessFigureColor: ChessFigureColor,
    val user: User
) {
    var canMove: Boolean = false
    val cursor: ObjectProperty<Cursor> = SimpleObjectProperty(this, "cursor")
    lateinit var king: ChessKing

}