package git.dimitrikvirik.chessgamedesktop.util

import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.*
import java.time.temporal.ChronoUnit
import java.util.*


object FileUtil {

    private const val datePattern = "yyyy-MM-dd-HH-mm-ss"

    private lateinit var currentRecord: File

    fun createRecord() {
        try {
            val format = SimpleDateFormat(datePattern).format(Date())

            val file = File("$format.csv")
            currentRecord = file
            if (file.createNewFile()) {
                println("Record created: " + file.name)
            }
        } catch (e: IOException) {
            println("An error occurred.")
            e.printStackTrace()
        }
    }

    fun writeRecord(gameMessage: GameMessage) {
        val fileWriter = FileWriter(currentRecord.path, true)
        val writer = CSVWriter(fileWriter)
        writer.use {
            val stringArray = arrayOf(
                gameMessage.step.toString(),
                gameMessage.fromMove.first.toString() + gameMessage.fromMove.second.toString(),
                gameMessage.toMove.first.toString() + gameMessage.toMove.second.toString(),
                gameMessage.action,
                gameMessage.sendTime.toString()
            )

            writer.writeNext(stringArray)
            writer.flush()

        }
    }

    fun readRecord(filename: String, handler: (GameMessage) -> Unit) {
        val reader = File(filename).bufferedReader()
        val csvReader = CSVReader(reader)

        csvReader.use {
            var line = csvReader.readNext()
            var diffTime: Long? = null
            while (line != null) {
                val gameMessage = GameMessage(
                    Pair(line[1][0].toString().toInt(), line[1][1].toString().toInt()),
                    Pair(line[2][0].toString().toInt(), line[2][1].toString().toInt()),
                    line[3],
                    line[0].toString().toInt(),
                    LocalDateTime.parse(line[4])
                )
                if (diffTime == null) {
                    val fromInstant = gameMessage.sendTime!!.toInstant(ZoneOffset.ofHours(4))
                    val currentInstant = Instant.now(Clock.systemUTC());

                    val fromTime = fromInstant.toEpochMilli()
                    diffTime = currentInstant.toEpochMilli() - fromTime
                }
                val time = gameMessage.sendTime!!.plus(Duration.ofMillis(diffTime))
                var nowTime = LocalDateTime.now()

                while (!nowTime.truncatedTo(ChronoUnit.MILLIS)
                        .isEqual(time.truncatedTo(ChronoUnit.MILLIS))
                ) {
                    nowTime = LocalDateTime.now()
                }
                handler(gameMessage)
                println("READING ${gameMessage.step} STEP")
                line = csvReader.readNext()
            }
        }

    }


}