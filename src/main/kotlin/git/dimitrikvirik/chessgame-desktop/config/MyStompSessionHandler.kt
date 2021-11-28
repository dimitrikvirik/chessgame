package git.dimitrikvirik.chessgame.config

import org.springframework.messaging.simp.stomp.*
import org.springframework.stereotype.Component
import java.lang.reflect.Type


@Component
class MyStompSessionHandler : StompSessionHandler {

    override fun getPayloadType(p0: StompHeaders): Type {
        return p0::class.java
    }

    override fun handleFrame(p0: StompHeaders, p1: Any?) {
        println("received $p0");
    }

    override fun afterConnected(stompSession: StompSession, stompHeaders: StompHeaders) {
        println("Connected!")
    }

    override fun handleException(p0: StompSession, p1: StompCommand?, p2: StompHeaders, p3: ByteArray, p4: Throwable) {
        println("exception!")
    }

    override fun handleTransportError(p0: StompSession, p1: Throwable) {
       println("transport error!")
        p1.printStackTrace()
    }
}