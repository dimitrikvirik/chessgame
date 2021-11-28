package git.dimitrikvirik.chessgame

import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ChessgameApplication

fun main(args: Array<String>) {
    Application.launch(ChartApplication::class.java, *args)
}
