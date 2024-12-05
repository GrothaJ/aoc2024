package org.example

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("src", "main", "resources", "day3.txt")
    val lines = Files.readAllLines(path)
        .filterNot { it.isBlank() }
    var result1 = 0
    var isEnabled = true
    // task 1
    for (line in lines) {
        for (idx in 0..<line.length - 7) {
            val multiplicationResult = checkForMul(line, idx)
            result1 += multiplicationResult
        }
    }
    println(result1)

    // task 2
    var result2 = 0
    var lineNumber = 0
    for (line in lines) {
        for (idx in 0..<line.length - 7) {
            if (isEnabled) {
                val disabled = checkForDisabled(line, idx)
                if (disabled) {
                    isEnabled = false
                }
            } else {
                val enabled = checkForEnabled(line, idx)
                if (enabled) {
                    isEnabled = true
                }
            }
            val multiplicationResult = checkForMul(line, idx)
            if (isEnabled) {
                result2 += multiplicationResult
            }
        }
        lineNumber++
    }
    println(result2)
}

fun checkForMul(line: String, idx: Int): Int {
    var firstLength = 0
    var secondLength = 0
    if (line[idx] != 'm') return 0
    if (line[idx + 1] != 'u') return 0
    if (line[idx + 2] != 'l') return 0
    if (line[idx + 3] != '(') return 0
    // check first number
    val lPos1 = line[idx + 4]
    if (lPos1.isDigit()) {
        firstLength++
        val lPos2 = line[idx + 5]
        if (lPos2.isDigit()) {
            firstLength++
            val lPos3 = line[idx + 6]
            if (lPos3.isDigit()) {
                firstLength++
            } else if (lPos3 != ',') {
                return 0
            }
        } else if (lPos2 != ',') {
            return 0
        }
    } else {
        return 0
    }
    // check second number
    val rPos1 = line[idx + 5 + firstLength]
    if (rPos1.isDigit()) {
        secondLength++
        val rPos2 = line[idx + 5 + firstLength + 1]
        if (rPos2.isDigit()) {
            secondLength++
            val rPos3 = line[idx + 5 + firstLength + 2]
            if (rPos3.isDigit()) {
                secondLength++
                val rPos4 = line[idx + 5 + firstLength + 3]
                if (rPos4 != ')') {
                    return 0
                }
            } else if (rPos3 != ')') {
                return 0
            }
        } else if (rPos2 != ')') {
            return 0
        }
    } else {
        return 0
    }
    // form numbers
    val num1 = line.substring(idx + 4, idx + 4 + firstLength).toInteger()
    val num2 = line.substring(idx + 5 + firstLength, idx + 5 + firstLength + secondLength).toInteger()
    return num1 * num2
}

// don't()
fun checkForDisabled(line: String, idx: Int) = line.substring(idx, idx + 7) == "don't()"

// do()
fun checkForEnabled(line: String, idx: Int) = line.substring(idx, idx + 4) == "do()"
