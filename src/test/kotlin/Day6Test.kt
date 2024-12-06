import org.example.Day6
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class Day6Test {

    private val day6 = Day6()
    private val path = Paths.get("src", "test", "resources", "day6.txt")

    @Test
    fun `should solve task 1`() {
        // given
        val input = Files.readAllLines(path)

        // when
        val result = day6.solveTask1(input)

        // then
        Assertions.assertEquals(41, result)
    }

    @Test
    fun `should solve task 2`() {
        // given
        val input = Files.readAllLines(path)

        // when
        val result= day6.solveTask2(input)

        // then
        Assertions.assertEquals(6, result)
    }
}
