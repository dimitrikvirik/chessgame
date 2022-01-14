package git.dimitrikvirik.chessgamedesktop.util

import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import git.dimitrikvirik.chessgamedesktop.controller.GameBoardController
import git.dimitrikvirik.chessgamedesktop.core.BeanContext
import git.dimitrikvirik.chessgamedesktop.core.model.Coordination
import git.dimitrikvirik.chessgamedesktop.core.model.GameMessage
import git.dimitrikvirik.chessgamedesktop.core.model.PlayerMessage
import javafx.application.Platform
import org.springframework.aop.ThrowsAdvice
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.time.*
import java.time.temporal.ChronoUnit


object FileUtil {

    private const val datePattern = "yyyy-MM-dd-HH-mm-ss"

    private lateinit var currentRecord: File

    fun createRecord(gameId: String) {
        try {
            val file = File("$gameId.csv")
            currentRecord = file
            if (file.createNewFile()) {
                println("Record created: " + file.name)
            } else {
                clear(file)
            }
        } catch (e: IOException) {
            println("An error occurred.")
            e.printStackTrace()
        }
    }

    fun clear(file: File) {
        val writer = PrintWriter(file)
        writer.print("")
        writer.close()
    }

    fun writePlayersRecord(playerMessage: PlayerMessage) {
        val fileWriter = FileWriter(currentRecord.path, true)
        val writer = CSVWriter(fileWriter)
        writer.use {
            val stringArray = arrayOf(
                "PLAYER",
                playerMessage.senderPlayerName,
                playerMessage.whitePlayerName,
                playerMessage.blackPlayerName,
                playerMessage.whiteConnected.toString(),
                playerMessage.blackConnected.toString(),
                LocalDateTime.now().toString()
            )

            writer.writeNext(stringArray)
            writer.flush()
        }

    }

    fun writeRecord(gameMessage: GameMessage) {
        val fileWriter = FileWriter(currentRecord.path, true)
        val writer = CSVWriter(fileWriter)
        writer.use {
            val stringArray = arrayOf(
                "STEP",
                gameMessage.step.toString(),
                Coordination.toChess(gameMessage.fromMove),
                Coordination.toChess(gameMessage.toMove),
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
        val gameBoardController = BeanContext.getBean(GameBoardController::class.java)

        csvReader.use {
            var line = csvReader.readNext()
            var diffTime: Long? = null
            var loadedGame = false

            while (line != null) {
                if (line[0] == "PLAYER") {
                    val playerMessage = PlayerMessage(
                        line[1],
                        line[2],
                        line[3],
                        line[4].toBoolean(),
                        line[5].toBoolean()
                    )

                    gameBoardController.setUser(playerMessage)
                    Platform.runLater {
                        gameBoardController.updateUserText()
                        if(!loadedGame){
                            gameBoardController.loadGame()
                            loadedGame = true
                        }

                    }


                }
                if (line[0] == "STEP") {
                    val gameMessage = GameMessage(
                        Coordination.fromChess(line[2]),
                        Coordination.fromChess(line[3]),
                        line[4],
                        line[1].toString().toInt(),
                        LocalDateTime.parse(line[5])
                    )

                    if (diffTime == null) {
                        val fromInstant = gameMessage.sendTime!!.toInstant(ZoneOffset.ofHours(4))
                        val currentInstant = Instant.now(Clock.systemUTC())

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
                }

                line = csvReader.readNext()

            }

        }
    }
}
