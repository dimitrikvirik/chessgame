package git.dimitrikvirik.chessgamedesktop.core

import lombok.RequiredArgsConstructor
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.stereotype.Component
import java.net.MalformedURLException
import java.net.URL


@Component
@RequiredArgsConstructor
class FXMLScanner {
    fun scan(rootPath: String = "/view") {
        val files: ArrayList<Pair<URL, String>> = ArrayList()
        val cl = FXMLScanner::class.java.classLoader
        val resolver: ResourcePatternResolver = PathMatchingResourcePatternResolver(cl)
        val resources: Array<Resource> = resolver.getResources("classpath*:**/*.fxml")
        for (resource in resources) {
            files.add(resource.url to "game-board.fxml")
        }
        for (file in files) {
            try {
                SceneBuilder.build(file)
            } catch (e: MalformedURLException) {
                return
            }
        }
    }

}
