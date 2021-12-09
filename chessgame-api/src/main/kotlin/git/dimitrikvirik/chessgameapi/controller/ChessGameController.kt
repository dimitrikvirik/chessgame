package git.dimitrikvirik.chessgameapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import git.dimitrikvirik.chessgameapi.model.game.ChessMessage
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController


@RestController
class ChessGameController {


    fun getChessGame(){

    }
    data class Message(val message: ByteArray){
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



    @MessageMapping("/chessgame/{gameId}")
    @SendTo("/topic/chessgame/{gameId}")
    fun getMoves(message: Message, @DestinationVariable gameId: String): Message {
        return message

    }
}