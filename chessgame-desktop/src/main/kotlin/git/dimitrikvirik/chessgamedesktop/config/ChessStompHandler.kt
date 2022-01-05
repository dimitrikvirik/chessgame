package git.dimitrikvirik.chessgamedesktop.config

import git.dimitrikvirik.chessgamedesktop.model.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import javafx.application.Platform
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Type


@Component
class ChessStompHandler(
    val chessGame: ChessGame
) : StompSessionHandler {


    override fun getPayloadType(p0: StompHeaders): Type {
        return ChessMessage::class.java
    }

    override fun handleFrame(p0: StompHeaders, p1: Any?) {
        val chessMessage = p1 as ChessMessage
        if(chessMessage.step != chessGame.currentStep + 1){
            println("Package lost!")
        }
        println("received $p0")


        Platform.runLater {
            chessGame.handleMessage(p1)
        }

    }

    override fun afterConnected(stompSession: StompSession, stompHeaders: StompHeaders) {
        println("Connected")
    }

    override fun handleException(p0: StompSession, p1: StompCommand?, p2: StompHeaders, p3: ByteArray, p4: Throwable) {
        println("exception!")
        p4.printStackTrace()
    }

    override fun handleTransportError(p0: StompSession, p1: Throwable) {
        println("transport error!")
        p1.printStackTrace()
    }
}