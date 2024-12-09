import org.example.Day9
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class Day9Test {

    private val day9 = Day9()
    private val path = Paths.get("src", "test", "resources", "day9.txt")

    @Test
    fun `should solve task 1`() {
        // given
        val input = Files.readString(path).trim()

        // when
        val result = day9.solveTask1(input)

        // then
        Assertions.assertEquals(1928L, result)
    }

    @Test
    fun `should solve task2`() {
        // given
        val input = Files.readString(path).trim()

        // when
        val result = day9.solveTask2(input)

        // then
        Assertions.assertEquals(2858, result)
    }
}
