package git.dimitrikvirik.chessgameapi.controller;

import git.dimitrikvirik.chessgameapi.model.Message;
import git.dimitrikvirik.chessgameapi.model.enums.ChessPlayerColor;
import git.dimitrikvirik.chessgameapi.model.game.ChessMessage;
import git.dimitrikvirik.chessgameapi.model.param.GameJoinParam;
import git.dimitrikvirik.chessgameapi.model.redis.ChessPlayer;
import git.dimitrikvirik.chessgameapi.model.redis.Game;
import git.dimitrikvirik.chessgameapi.service.GameRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
public class ChessGameController {

    private final GameRedisService gameRedisService;

    @PostMapping
    public ResponseEntity<Game> createGame() {
        return ResponseEntity.ok(gameRedisService.create());
    }


    @PostMapping("/join")
    public Game joinGame(@RequestBody GameJoinParam gameJoinParam, @RequestHeader String userId) {
        Game game = gameRedisService.get(gameJoinParam.getGameId());
        if (gameJoinParam.getColor().equals(ChessPlayerColor.WHITE)) {
            if (game.getWhitePlayer() != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "White player taken!");
            }
            ChessPlayer whitePlayer = new ChessPlayer();
            whitePlayer.setColor(ChessPlayerColor.WHITE);
            whitePlayer.setUserId(userId);
            game.setWhitePlayer(whitePlayer);
        }
        if (gameJoinParam.getColor().equals(ChessPlayerColor.BLACK)) {
            if (game.getBlackPlayer() != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Black player taken!");
            }
            ChessPlayer blackPlayer = new ChessPlayer();
            blackPlayer.setColor(ChessPlayerColor.BLACK);
            blackPlayer.setUserId(userId);
            game.setBlackPlayer(blackPlayer);
        }
        return gameRedisService.update(game);
    }

    @GetMapping("/load/{gameId}")
    public List<ChessMessage> getMessages(@RequestParam Long from, @RequestParam Long to, @PathVariable String gameId){

        Game game = gameRedisService.get(gameId);
        List<ChessMessage> messages = game.getMessages();
        messages.stream().filter(m -> {
            m.getStep() >= from
        })

    }


    @MessageMapping({"/chessgame/{gameId}"})
    @SendTo({"/topic/chessgame/{gameId}"})
    public ChessMessage getMoves(ChessMessage message, @DestinationVariable String gameId) {

        Game game = gameRedisService.get(gameId);
        List<ChessMessage> messages = game.getMessages();
        messages.add(message);
        gameRedisService.update(game);
        log.info(message + " " + messages.size());
        message.setStep(message.getStep() + 1);
        return message;
    }
}