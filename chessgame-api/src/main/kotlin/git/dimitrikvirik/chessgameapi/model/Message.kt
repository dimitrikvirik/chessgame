package git.dimitrikvirik.chessgameapi.model

data class Message(val message: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (!message.contentEquals(other.message)) return false

        return true
    }

    override fun hashCode(): Int {
        return message.contentHashCode()
    }
}
