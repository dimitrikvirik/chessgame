package git.dimitrikvirik.chessgamedesktop

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import git.dimitrikvirik.chessgame.ChartApplication
import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

@SpringBootApplication
class ChessgameApplication{
    @Bean
    fun websocket(): WebSocketStompClient {
        val client: WebSocketClient = StandardWebSocketClient()

        val stompClient = WebSocketStompClient(client)
        val mappingJackson2MessageConverter = MappingJackson2MessageConverter()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule.Builder().build())
        mappingJackson2MessageConverter.objectMapper = objectMapper
        stompClient.messageConverter = mappingJackson2MessageConverter
        return stompClient;

    }
}

fun main(args: Array<String>) {
    Application.launch(ChartApplication::class.java, *args)
}

