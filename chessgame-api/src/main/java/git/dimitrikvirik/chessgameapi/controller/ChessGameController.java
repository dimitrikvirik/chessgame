package git.dimitrikvirik.chessgameapi.controller;

import git.dimitrikvirik.chessgameapi.model.enums.ChessPlayerColor;
import git.dimitrikvirik.chessgameapi.model.game.GameMessage;
import git.dimitrikvirik.chessgameapi.model.param.GameJoinParam;
import git.dimitrikvirik.chessgameapi.model.redis.ChessPlayer;
import git.dimitrikvirik.chessgameapi.model.redis.Game;
import git.dimitrikvirik.chessgameapi.service.GameRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
public class ChessGameController {

    private final GameRedisService gameRedisService;

    private final SimpMessagingTemplate template;

    @PostMapping
    public ResponseEntity<Game> createGame() {
        return ResponseEntity.ok(gameRedisService.create());
    }


    @MessageMapping({"/player/{gameId}"})
    @SendTo({"/topic/player/{gameId}"})
    public GameJoinParam joinGame(@DestinationVariable String gameId, @Payload GameJoinParam gameJoinParam, SimpMessageHeaderAccessor headerAccessor) {
        Game game = gameRedisService.get(gameId);
        log.info(headerAccessor.getSessionId());
        //8ae69dd8-2a6c-40b6-881c-33907e26146d

        setPlayer(headerAccessor, gameJoinParam, game);
        setPlayer(game, gameJoinParam);
        if (gameJoinParam.getSenderPlayerName().equals(gameJoinParam.getWhitePlayerName())) {
            game.getWhitePlayer().setConnected(true);
            gameJoinParam.setWhiteConnected(true);
            gameJoinParam.setWhitePlayerName(gameJoinParam.getWhitePlayerName());
        } else {
            game.getBlackPlayer().setConnected(true);
            gameJoinParam.setBlackConnected(true);
            gameJoinParam.setBlackPlayerName(gameJoinParam.getBlackPlayerName());
        }

        gameRedisService.update(game);
        return gameJoinParam;
    }


    @GetMapping("/game-record/{gameId}")
    public Game getGame(@PathVariable String gameId){
        return gameRedisService.get(gameId);
    }


    private void setPlayer(SimpMessageHeaderAccessor headerAccessor, GameJoinParam gameJoinParam, Game game) {
        if (game.getBlackPlayer() != null &&  gameJoinParam.getSenderPlayerName().equals(game.getBlackPlayer().getUserId())) {
            game.setBlackPlayerSessionId(headerAccessor.getSessionId());
            game.getBlackPlayer().setConnected(true);
            return;
        }
        if (game.getWhitePlayer() != null &&   gameJoinParam.getSenderPlayerName().equals(game.getWhitePlayer().getUserId())) {
            game.setWhitePlayerSessionId(headerAccessor.getSessionId());
            game.getWhitePlayer().setConnected(true);
            return;
        }
        if (game.getWhitePlayer() == null && game.getBlackPlayer() == null) {
            final int random = new Random().nextInt(2);
            if (random == 0) {
                addWhitePlayer(headerAccessor, gameJoinParam, game);
            } else addBlackPlayer(headerAccessor, gameJoinParam, game);
        } else if (game.getWhitePlayer() != null) {
            addBlackPlayer(headerAccessor, gameJoinParam, game);
        } else if (game.getBlackPlayer() != null) {
            addWhitePlayer(headerAccessor, gameJoinParam, game);
        }
    }

    private void addBlackPlayer(SimpMessageHeaderAccessor headerAccessor, GameJoinParam gameJoinParam, Game game) {
        ChessPlayer blackPlayer = new ChessPlayer();
        blackPlayer.setColor(ChessPlayerColor.BLACK);
        blackPlayer.setUserId(gameJoinParam.getSenderPlayerName());
        game.setBlackPlayerSessionId(headerAccessor.getSessionId());
        game.setBlackPlayer(blackPlayer);
        game.getBlackPlayer().setConnected(true);
    }

    private void addWhitePlayer(SimpMessageHeaderAccessor headerAccessor, GameJoinParam gameJoinParam, Game game) {
        ChessPlayer whitePlayer = new ChessPlayer();
        whitePlayer.setColor(ChessPlayerColor.WHITE);
        whitePlayer.setUserId(gameJoinParam.getSenderPlayerName());
        game.setWhitePlayer(whitePlayer);
        game.setWhitePlayerSessionId(headerAccessor.getSessionId());
        game.getWhitePlayer().setConnected(true);
    }

    @GetMapping("/load/{gameId}")
    public List<GameMessage> getMessages(@RequestParam(defaultValue = "0") Long from, @PathVariable String gameId) {

        Game game = gameRedisService.get(gameId);
        List<GameMessage> messages = game.getMessages();
        return messages.stream().filter(e -> e.getStep() > from).collect(Collectors.toList());

    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        final Game game = gameRedisService.getBySessionId(event.getSessionId());
        if (game != null) {

            GameJoinParam gameJoinParam = new GameJoinParam();


            setPlayer(game, gameJoinParam);
            gameJoinParam.setSenderPlayerName("_SERVER_");
            if (game.getWhitePlayerSessionId().equals(event.getSessionId())) {
                gameJoinParam.setWhiteConnected(false);
                game.getWhitePlayer().setConnected(false);
            } else {
                game.getBlackPlayer().setConnected(false);
                gameJoinParam.setBlackConnected(false);
            }
            if (game.getWhitePlayer() != null) {
                gameJoinParam.setWhiteConnected(gameJoinParam.getWhiteConnected());
            }
            if (game.getBlackPlayer() != null) {
                gameJoinParam.setBlackConnected(gameJoinParam.getBlackConnected());
            }
            template.convertAndSend("/topic/player/" + game.getId(), gameJoinParam);


            gameRedisService.update(game);

            log.info("User was disconnected from game with id {}", game.getId());
        }
    }

    private void setPlayer(Game game, GameJoinParam gameJoinParam) {
        if (game.getWhitePlayer() != null) {
            gameJoinParam.setWhiteConnected(game.getWhitePlayer().getConnected());
            gameJoinParam.setWhitePlayerName(game.getWhitePlayer().getUserId());
        }
        if (game.getBlackPlayer() != null) {
            gameJoinParam.setBlackConnected(game.getBlackPlayer().getConnected());
            gameJoinParam.setBlackPlayerName(game.getBlackPlayer().getUserId());
        }
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