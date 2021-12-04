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




    @MessageMapping("/chessgame/{gameId}")
    @SendTo("/topic/chessgame/{gameId}")
    fun getMoves(message: ChessMessage, @DestinationVariable gameId: String): ChessMessage {
        println(ObjectMapper().convertValue(message, LinkedHashMap::class.java))
        return message;

    }
}