package org.example

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("src", "main", "resources", "day4.txt")
    val lines = Files.readAllLines(path)
        .filterNot { it.isBlank() }
    // task 1
    var result1 = 0
    for (i in lines.indices) {
        for (j in 0..<lines[i].length) {
            val firstLetter = lines[i][j]
            if (firstLetter != 'X') continue
            if (checkUp(i, j, lines)) result1++
            if (checkRight(i, j, lines)) result1++
            if (checkLeft(i, j, lines)) result1++
            if (checkDown(i, j, lines)) result1++
            if (checkUpRight(i, j, lines)) result1++
            if (checkDownRight(i, j, lines)) result1++
            if (checkDownLeft(i, j, lines)) result1++
            if (checkUpLeft(i, j, lines)) result1++
        }
    }
    println(result1)

    // task 2
    var result2 = 0
    for (i in lines.indices) {
        for (j in 0..<lines[i].length) {
            var oneIsRight = false
            val firstLetter = lines[i][j]
            if (firstLetter != 'A') continue
            if (i > 0 && j > 0 && i < lines.size - 1 && j < lines.first().length - 1) {
                // top right
                if (lines[i - 1][j + 1] == 'M' && lines[i + 1][j - 1] == 'S') oneIsRight = true
                // bottom right
                if (lines[i + 1][j + 1] == 'M' && lines[i - 1][j - 1] == 'S') {
                    if (oneIsRight) result2++ else oneIsRight = true
                }
                // bottom left
                if (lines[i + 1][j - 1] == 'M' && lines[i - 1][j + 1] == 'S') {
                    if (oneIsRight) result2++ else oneIsRight = true
                }
                // top left
                if (lines[i - 1][j - 1] == 'M' && lines[i + 1][j + 1] == 'S') {
                    if (oneIsRight) result2++
                }
            }
        }
    }
    println(result2)
}

fun checkUp(i: Int, j: Int, lines: List<String>): Boolean {
    if (i < 3) return false
    if (lines[i - 1][j] != 'M') return false
    if (lines[i - 2][j] != 'A') return false
    return lines[i - 3][j] == 'S'
}

fun checkRight(i: Int, j: Int, lines: List<String>): Boolean {
    if (j > lines.first().length - 4) return false
    if (lines[i][j + 1] != 'M') return false
    if (lines[i][j + 2] != 'A') return false
    return (lines[i][j + 3] == 'S')
}

fun checkLeft(i: Int, j: Int, lines: List<String>): Boolean {
    if (j < 3) return false
    if (lines[i][j - 1] != 'M') return false
    if (lines[i][j - 2] != 'A') return false
    return lines[i][j - 3] == 'S'
}

fun checkDown(i: Int, j: Int, lines: List<String>): Boolean {
    if (i > lines.size - 4) return false
    if (lines[i + 1][j] != 'M') return false
    if (lines[i + 2][j] != 'A') return false
    return lines[i + 3][j] == 'S'
}

fun checkUpRight(i: Int, j: Int, lines: List<String>): Boolean {
    if (i < 3 || j > lines.first().length - 4) return false
    if (lines[i - 1][j + 1] != 'M') return false
    if (lines[i - 2][j + 2] != 'A') return false
    return lines[i - 3][j + 3] == 'S'
}

fun checkDownRight(i: Int, j: Int, lines: List<String>): Boolean {
    if (i > lines.size - 4 || j > lines.first().length - 4) return false
    if (lines[i + 1][j + 1] != 'M') return false
    if (lines[i + 2][j + 2] != 'A') return false
    return lines[i + 3][j + 3] == 'S'
}

fun checkDownLeft(i: Int, j: Int, lines: List<String>): Boolean {
    if (j < 3 || i > lines.size - 4) return false
    if (lines[i + 1][j - 1] != 'M') return false
    if (lines[i + 2][j - 2] != 'A') return false
    return lines[i + 3][j - 3] == 'S'
}

fun checkUpLeft(i: Int, j: Int, lines: List<String>): Boolean {
    if (i < 3 || j < 3) return false
    if (lines[i - 1][j - 1] != 'M') return false
    if (lines[i - 2][j - 2] != 'A') return false
    return lines[i - 3][j - 3] == 'S'
}

