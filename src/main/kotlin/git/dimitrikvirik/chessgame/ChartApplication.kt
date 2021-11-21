package git.dimitrikvirik.chessgame;

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext

class ChartApplication : Application() {
    private var applicationContext: ConfigurableApplicationContext? = null
    override fun init() {
        applicationContext = SpringApplicationBuilder(ChessgameApplication::class.java).run()
    }

    override fun stop() {
        applicationContext!!.close()
        Platform.exit()
    }

    override fun start(stage: Stage) {
        applicationContext!!.publishEvent(StageReadyEvent(stage))
    }

    class StageReadyEvent(stage: Stage?) : ApplicationEvent(stage!!) {
        val stage: Stage
            get() = getSource() as Stage
    }
}
