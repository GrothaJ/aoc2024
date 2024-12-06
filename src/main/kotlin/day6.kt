package org.example

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("src", "main", "resources", "day6.txt")
    val lines = Files.readAllLines(path)
    val day6 = Day6()
    val solution1 = day6.solveTask1(lines)
    println("Solution 1: $solution1")
    val solution2 = day6.solveTask2(lines)
    println("Solution 2: $solution2")
}

class Day6 {

    fun solveTask1(lines: List<String>): Int {
        val startingPosition = findGuardStartingPos(lines)
        var currentPosition = startingPosition
        var currentStrategy: Strategy = MoveUp()
        val visited = mutableSetOf<Pair<Int, Int>>()
        var isOn = true
        while (isOn) {
            try {
                currentStrategy = getNextStrategy(currentStrategy, lines, currentPosition)
                currentPosition = currentStrategy.move(currentPosition)
                visited.add(currentPosition)
            } catch (_: IndexOutOfBoundsException) {
                isOn = false
            }
        }
        return visited.size
    }

    fun solveTask2(lines: List<String>): Int {
        var result = 0
        val startingPosition = findGuardStartingPos(lines)
        for ((i, line) in lines.withIndex()) {
            for ((j, char) in line.withIndex()) {
                var currentPosition = startingPosition
                var currentStrategy: Strategy = MoveUp()
                if (charArrayOf('^', '#').contains(char)) continue
                val obstaclePosition = i to j
                val visitedToStrategy = mutableSetOf<Pair<Pair<Int, Int>, String>>()
                var isOn = true
                var isNotLoop = true
                while (isOn && isNotLoop) {
                    try {
                        currentStrategy = getNextStrategy(currentStrategy, lines, currentPosition, obstaclePosition)
                        currentPosition = currentStrategy.move(currentPosition)
                        if (visitedToStrategy.contains(currentPosition to currentStrategy.javaClass.name)) {
                            result++
                            isNotLoop = false
                        }
                        visitedToStrategy.add(currentPosition to currentStrategy.javaClass.name)
                    } catch (_: IndexOutOfBoundsException) {
                        isOn = false
                    }
                }
            }
        }
        return result
    }

    private fun findGuardStartingPos(lines: List<String>): Pair<Int, Int> {
        for ((i, line) in lines.withIndex()) {
            for ((j, char) in line.withIndex()) {
                if ('^' == char) return i to j
            }
        }
        throw IllegalStateException()
    }

    private fun getNextStrategy(
        currentStrategy: Strategy,
        lines: List<String>,
        currentPosition: Pair<Int, Int>,
        obstaclePosition: Pair<Int, Int> = -1 to -1
    ): Strategy {
        var isStrategyInvalid = true
        var strategy = currentStrategy
        while (isStrategyInvalid) {
            val previousStrategy = strategy
            strategy = strategy.changeStrategyIfNeeded(lines, currentPosition, obstaclePosition)
            if (previousStrategy.javaClass.name == strategy.javaClass.name) isStrategyInvalid = false
        }
        return strategy
    }

    interface Strategy {
        fun changeStrategyIfNeeded(lines: List<String>, currentPosition: Pair<Int, Int>, obstaclePosition: Pair<Int, Int>): Strategy
        fun move(currentPosition: Pair<Int, Int>): Pair<Int, Int>
    }

    class MoveUp : Strategy {
        override fun changeStrategyIfNeeded(lines: List<String>, currentPosition: Pair<Int, Int>, obstaclePosition: Pair<Int, Int>): Strategy {
            val nextPosition = move(currentPosition)
            if (lines[nextPosition.first][nextPosition.second] == '#' || nextPosition == obstaclePosition) {
                return MoveRight()
            }
            return this
        }

        override fun move(currentPosition: Pair<Int, Int>): Pair<Int, Int> {
            return currentPosition.first - 1 to currentPosition.second
        }
    }

    class MoveRight : Strategy {
        override fun changeStrategyIfNeeded(lines: List<String>, currentPosition: Pair<Int, Int>, obstaclePosition: Pair<Int, Int>): Strategy {
            val nextPosition = move(currentPosition)
            if (lines[nextPosition.first][nextPosition.second] == '#' || nextPosition == obstaclePosition) {
                return MoveDown()
            }
            return this
        }

        override fun move(currentPosition: Pair<Int, Int>): Pair<Int, Int> {
            return currentPosition.first to currentPosition.second + 1
        }

    }

    class MoveDown : Strategy {
        override fun changeStrategyIfNeeded(lines: List<String>, currentPosition: Pair<Int, Int>, obstaclePosition: Pair<Int, Int>): Strategy {
            val nextPosition = move(currentPosition)
            if (lines[nextPosition.first][nextPosition.second] == '#' || nextPosition == obstaclePosition) {
                return MoveLeft()
            }
            return this
        }

        override fun move(currentPosition: Pair<Int, Int>): Pair<Int, Int> {
            return currentPosition.first + 1 to currentPosition.second
        }

    }

    class MoveLeft : Strategy {
        override fun changeStrategyIfNeeded(lines: List<String>, currentPosition: Pair<Int, Int>, obstaclePosition: Pair<Int, Int>): Strategy {
            val nextPosition = move(currentPosition)
            if (lines[nextPosition.first][nextPosition.second] == '#' || nextPosition == obstaclePosition) {
                return MoveUp()
            }
            return this
        }

        override fun move(currentPosition: Pair<Int, Int>): Pair<Int, Int> {
            return currentPosition.first to currentPosition.second - 1
        }
    }
}
