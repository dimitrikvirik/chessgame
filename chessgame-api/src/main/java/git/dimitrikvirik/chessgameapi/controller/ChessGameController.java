package git.dimitrikvirik.chessgameapi.controller;

import git.dimitrikvirik.chessgameapi.model.Message;
import git.dimitrikvirik.chessgameapi.model.redis.Game;
import git.dimitrikvirik.chessgameapi.service.GameRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class ChessGameController {

    private final GameRedisService gameRedisService;

    @PostMapping
    public ResponseEntity<Game> createGame() {
        return ResponseEntity.ok(gameRedisService.create());
    }

    @MessageMapping({"/chessgame/{gameId}"})
    @SendTo({"/topic/chessgame/{gameId}"})
    public Message getMoves(Message message, @DestinationVariable String gameId) {
        return message;
    }
}