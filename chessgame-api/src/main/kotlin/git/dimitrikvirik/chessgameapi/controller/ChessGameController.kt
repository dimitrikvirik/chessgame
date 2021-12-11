package git.dimitrikvirik.chessgameapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import git.dimitrikvirik.chessgameapi.model.Message
import git.dimitrikvirik.chessgameapi.model.game.ChessMessage
import git.dimitrikvirik.chessgameapi.model.redis.Game
import git.dimitrikvirik.chessgameapi.service.GameService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/game")
class ChessGameController(
    val gameService: GameService
) {


    @PostMapping
    fun createGame(): Game {
        return gameService.create()
    }


    @MessageMapping("/chessgame/{gameId}")
    @SendTo("/topic/chessgame/{gameId}")
    fun getMoves(message: Message, @DestinationVariable gameId: String): Message {
        return message
    }
}