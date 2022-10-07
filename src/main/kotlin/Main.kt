import org.rollcall.alg.calculateE
import org.rollcall.alg.onlyRollCallFrequentlyAbsentStudents
import org.rollcall.alg.rollCallStudentsPartlyBasedOnGpa
import org.rollcall.core.extractSample
import org.rollcall.input.DsvInput
import org.rollcall.output.ConsoleOutput
import org.rollcall.output.JsonOutput
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {

    val rollCallNumber = 24
    val pathString = "data"
    val output = ConsoleOutput()
    val dsvInputList = Files.list(Path.of(pathString)).map { path ->
        DsvInput(path)
    }.toList()!!

    dsvInputList.forEach { extractSample(rollCallStudentsPartlyBasedOnGpa, it, output, rollCallNumber) }

    val rollCallScheme = output.read()

    val inputDataList = dsvInputList.map { it.read().first }

    val e = calculateE(inputDataList, rollCallScheme)

    output.output(e)
}
