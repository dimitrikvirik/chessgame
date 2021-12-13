package git.dimitrikvirik.chessgamedesktop.config

import git.dimitrikvirik.chessgamedesktop.model.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.service.ChessMessage
import git.dimitrikvirik.chessgamedesktop.service.Message
import javafx.application.Platform
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Type


@Component

class ChessStompHandler : StompSessionHandler {
    lateinit var chessGame: ChessGame



    val currentStep: Long = 0
    override fun getPayloadType(p0: StompHeaders): Type {
        return Message::class.java
    }

    override fun handleFrame(p0: StompHeaders, p1: Any?) {
        println("received $p0");
        val step =  p0.messageId!!.substringAfterLast("-").toLong()

        if(step == 2L || step == 1L){
            currentStep.plus(2)
        }
        else{
            if(currentStep + 2 != step){
                println("Package lost!")
            }
            else{
                currentStep.plus(2)
            }
        }

        val message = ChessMessage((p1 as Message).message)

        Platform.runLater {
            chessGame.handleMessage(message)
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