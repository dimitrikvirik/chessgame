package git.dimitrikvirik.chessgameapi

import git.dimitrikvirik.chessgameapi.repository.EntityScanner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChessgameApiApplication



fun main(args: Array<String>) {
    val context = runApplication<ChessgameApiApplication>(*args)
    val entityScanner = context.getBean(EntityScanner::class.java)
    entityScanner.scan("git.dimitrikvirik.chessgameapi.model.domain")
}
