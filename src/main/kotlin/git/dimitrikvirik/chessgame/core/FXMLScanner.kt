package git.dimitrikvirik.chessgame.core

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.io.File
import java.net.MalformedURLException

@Component
@RequiredArgsConstructor
class FXMLScanner {
    fun scan(rootPath: String = "/view") {
        val files: MutableList<File> = ArrayList()

        listf(FXMLScanner::class.java.getResource(rootPath)!!.path, files)
        for (file in files) {
            try {
                SceneBuilder.build(file)
            } catch (e: MalformedURLException) {
                return
            }
        }
    }

    private fun listf(directoryName: String, files: MutableList<File>) {
        val directory = File(directoryName)

        // Get all files from a directory.
        val fList = directory.listFiles()
        if (fList != null) for (file in fList) {
            if (file.isFile && file.absolutePath.endsWith(".fxml")) {
                files.add(file)
            } else if (file.isDirectory) {
                listf(file.absolutePath, files)
            }
        }
    }
}
