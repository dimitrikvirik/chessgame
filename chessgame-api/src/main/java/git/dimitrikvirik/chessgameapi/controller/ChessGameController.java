package git.dimitrikvirik.chessgameapi.controller;

import git.dimitrikvirik.chessgameapi.model.enums.ChessPlayerColor;
import git.dimitrikvirik.chessgameapi.model.game.GameMessage;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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


    @MessageMapping({"/player/{gameId}"})
    @SendTo({"/topic/player/{gameId}"})
    public GameJoinParam joinGame(@DestinationVariable String gameId, GameJoinParam gameJoinParam) {
        Game game = gameRedisService.get(gameId);
        if (game.getWhitePlayer() == null) {
            ChessPlayer whitePlayer = new ChessPlayer();
            whitePlayer.setColor(ChessPlayerColor.WHITE);
            whitePlayer.setUserId(gameJoinParam.getSenderPlayerName());
            game.setWhitePlayer(whitePlayer);


        } else if (game.getBlackPlayer() == null) {
            ChessPlayer blackPlayer = new ChessPlayer();
            blackPlayer.setColor(ChessPlayerColor.BLACK);
            blackPlayer.setUserId(gameJoinParam.getSenderPlayerName());
            game.setBlackPlayer(blackPlayer);

        } else if (!(game.getBlackPlayer().getUserId().equals(gameJoinParam.getSenderPlayerName()) ||
                game.getWhitePlayer().getUserId().equals(gameJoinParam.getSenderPlayerName())
        )) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Game started!");
        }
        if (game.getWhitePlayer() != null) {
            gameJoinParam.setWhiteConnected(true);
            gameJoinParam.setWhitePlayerName(game.getWhitePlayer().getUserId());
        }
        if (game.getBlackPlayer() != null) {
            gameJoinParam.setBlackConnected(true);
            gameJoinParam.setBlackPlayerName(game.getBlackPlayer().getUserId());
        }

        gameRedisService.update(game);
        return gameJoinParam;
    }

    @GetMapping("/load/{gameId}")
    public List<GameMessage> getMessages(@RequestParam(defaultValue = "0") Long from, @PathVariable String gameId) {

        Game game = gameRedisService.get(gameId);
        List<GameMessage> messages = game.getMessages();
        return messages.stream().filter(e -> e.getStep() > from).collect(Collectors.toList());

    }


    @MessageMapping({"/chessgame/{gameId}"})
    @SendTo({"/topic/chessgame/{gameId}"})
    public GameMessage getMoves(GameMessage message, @DestinationVariable String gameId) {

        message.setStep(message.getStep() + 1);
        message.setSendTime(LocalDateTime.now());
        Game game = gameRedisService.get(gameId);
        List<GameMessage> messages = game.getMessages();
        messages.add(message);
        gameRedisService.update(game);
        log.info(message + " " + messages.size());
        return message;
    }
}