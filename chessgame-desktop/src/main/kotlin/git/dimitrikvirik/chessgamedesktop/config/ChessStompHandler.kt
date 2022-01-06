package git.dimitrikvirik.chessgamedesktop.config

import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import git.dimitrikvirik.chessgamedesktop.util.FileUtil
import javafx.application.Platform
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Type


@Component
class ChessStompHandler : StompSessionHandler {


    override fun getPayloadType(p0: StompHeaders): Type {
        return GameMessage::class.java
    }

    override fun handleFrame(p0: StompHeaders, p1: Any?) {
        val chessMessage = p1 as GameMessage
        val chessGame = BeanContext.getBean(ChessGame::class.java)

        if (chessMessage.step != chessGame.currentStep + 1) {
            throw  IllegalArgumentException()
        }
        println("received $p0 \n $p1")


        Platform.runLater {
            chessGame.handle(p1)
        }
        FileUtil.writeRecord(p1)


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