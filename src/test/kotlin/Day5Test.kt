import org.example.Day5
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class Day5Test {

    private val day5 = Day5()
    private val path = Paths.get("src", "test", "resources", "day5.txt")

    @Test
    fun `should solve task 1`() {
        // given
        val lines = Files.readAllLines(path)

        // when
        val result = day5.solveTask1(lines)

        // then
        Assertions.assertEquals(143, result)
    }

    @Test
    fun `should solve task 2`() {
        // given
        val lines = Files.readAllLines(path)

        // when
        val result = day5.solveTask2(lines)

        // then
        Assertions.assertEquals(123, result)
    }
}
