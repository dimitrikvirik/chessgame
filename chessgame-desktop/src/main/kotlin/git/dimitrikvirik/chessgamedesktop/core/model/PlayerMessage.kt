package git.dimitrikvirik.chessgamedesktop.core.model

data class PlayerMessage(
    val senderPlayerName: String,
    val whitePlayerName: String? = null,
    val blackPlayerName: String? = null,
    val whiteConnected: Boolean = false,
    val blackConnected: Boolean = false
)
