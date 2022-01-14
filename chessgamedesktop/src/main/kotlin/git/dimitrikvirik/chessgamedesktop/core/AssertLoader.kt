package git.dimitrikvirik.chessgamedesktop.core

import com.opencsv.CSVReader
import git.dimitrikvirik.chessgamedesktop.game.ChessGame
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.apache.commons.io.IOUtils
import org.apache.commons.io.input.CharSequenceReader
import org.springframework.stereotype.Component
import java.io.Reader

@Component
class AssertLoader {

    val records: MutableMap<String, String> = mutableMapOf()

    init {
        val initialStream = AssertLoader::class.java.getResourceAsStream("/assert.csv")
        val buffer: ByteArray = IOUtils.toByteArray(initialStream)
        val targetReader: Reader = CharSequenceReader(String(buffer))

        CSVReader(targetReader).use { csvReader ->
            var values: Array<String?>?
            while (csvReader.readNext().also { values = it } != null) {
                records[values?.get(0)!!] = values?.get(1)!!
            }
        }
        println()
    }

    fun load(id: String): String {
        val root = "/assert/"
        val end = "_1x.png"
        return "$root${records[id]}$end"
    }

    fun loadSound(name: String) {
        if (BeanContext.getBean(ChessGame::class.java).gameLoaded) {
            val url = AssertLoader::class.java.getResource("/sounds/$name.mp3")
            val sound = Media(url.toString())
            MediaPlayer(sound).play()
        }
    }

}