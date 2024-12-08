import org.example.Day8
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class Day8Test {

    private val day8 = Day8()
    private val path = Paths.get("src", "test", "resources", "day8.txt")

    @Test
    fun `should solve task 1`() {
        // given
        val lines = Files.readAllLines(path)

        // when
        val result = day8.solveTask1(lines)

        // then
        Assertions.assertEquals(14, result)
    }

    @Test
    fun `should solve task 2`() {
        // given
        val lines = Files.readAllLines(path)

        // when
        val result = day8.solveTask2(lines)

        // then
        Assertions.assertEquals(34, result)
    }
}
