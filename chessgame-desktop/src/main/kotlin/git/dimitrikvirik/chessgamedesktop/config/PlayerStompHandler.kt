package git.dimitrikvirik.chessgamedesktop.config

import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.PlayerMessage
import javafx.application.Platform
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
class PlayerStompHandler : StompSessionHandler {
    override fun getPayloadType(headers: StompHeaders): Type {
        return PlayerMessage::class.java
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        println(payload)

        Platform.runLater {
            BeanContext.getBean(GameBoardController::class.java).setUser(payload as PlayerMessage)
        }
    }

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        println("Connected to PlayerSubscribe")
    }

    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable
    ) {
        exception.printStackTrace()
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        exception.printStackTrace()
    }
}