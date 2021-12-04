package git.dimitrikvirik.chessgamedesktop.model.game

import java.time.LocalDateTime

//TODO add actions
data class History<T>(val value: T) {
    val time: LocalDateTime = LocalDateTime.now()
}
