package git.dimitrikvirik.chessgame.model.game

import java.time.LocalDateTime

data class History<T>(val value: T) {
    val time: LocalDateTime = LocalDateTime.now()
}
