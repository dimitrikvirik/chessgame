package git.dimitrikvirik.chessgamedesktop.core

import com.opencsv.CSVReader
import org.apache.commons.io.IOUtils
import org.apache.commons.io.input.CharSequenceReader
import org.springframework.stereotype.Component
import java.io.FileReader
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
}