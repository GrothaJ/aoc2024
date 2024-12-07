import org.example.Day7
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class Day7Test {

    private val day7 = Day7()
    private val path = Paths.get("src", "test", "resources", "day7.txt")

    @Test
    fun `should solve task 1`() {
        // given
        val lines = Files.readAllLines(path)

        // when
        val result = day7.solveTask1(lines)

        // then
        Assertions.assertEquals(3749, result)
    }

    @Test
    fun `should solve task 2`() {
        // given
        val lines = Files.readAllLines(path)

        // when
        val result = day7.solveTask2(lines)

        // then
        Assertions.assertEquals(11387, result)
    }
}
