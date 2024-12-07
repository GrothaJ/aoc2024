package org.example

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("src", "main", "resources", "day7.txt")
    val lines = Files.readAllLines(path).filterNot { it.isBlank() }
    val day7 = Day7()
    val solution1 = day7.solveTask1(lines)
    println("Solution 1: $solution1")
    val solution2 = day7.solveTask2(lines)
    println("Solution 2: $solution2")
}

class Day7 {

    fun solveTask1(lines: List<String>): Long {
        var result = 0L
        val resultToNums = getResultToNums(lines)
        for (pair in resultToNums) {
            val expectedResult = pair.first
            val nums = pair.second
            val canBeSolved = applyOperation(nums, 0, expectedResult)
            if (canBeSolved) result += expectedResult
        }
        return result
    }

    fun solveTask2(lines: List<String>): Long {
        var result = 0L
        val resultToNums = getResultToNums(lines)
        for (pair in resultToNums) {
            val expectedResult = pair.first
            val nums = pair.second
            val canBeSolved = applyOperationForTask2(nums, 0, expectedResult)
            if (canBeSolved) result += expectedResult
        }
        return result
    }

    private fun getResultToNums(lines: List<String>): List<Pair<Long, List<Long>>> {
        val result = mutableListOf<Pair<Long, List<Long>>>()
        for (line in lines) {
            val resultToStringNums = line.split(":")
            val expectedResult = resultToStringNums.first().toLong()
            val nums = resultToStringNums[1].trim().split(" ").map { it.toLong() }
            result.add(expectedResult to nums)
        }
        return result
    }

    private fun applyOperation(nums: List<Long>, currentResult: Long, expectedResult: Long): Boolean {
        val num = nums.first()
        val newResultA = currentResult + num
        val newResultB = currentResult * num
        if (newResultA == expectedResult || newResultB == expectedResult) return true
        if (nums.size == 1) return false
        return applyOperation(nums.minus(nums.first()), newResultA, expectedResult) || applyOperation(nums.minus(nums.first()), newResultB, expectedResult)
    }

    private fun applyOperationForTask2(nums: List<Long>, currentResult: Long, expectedResult: Long): Boolean {
        val num = nums.first()
        val newResultA = currentResult + num
        val newResultB = currentResult * num
        val newResultC = ("$currentResult$num").toLong()
        return if (nums.size == 1) {
            (newResultA == expectedResult || newResultB == expectedResult || newResultC == expectedResult)
        } else {
            applyOperationForTask2(nums.minus(nums.first()), newResultA, expectedResult)
                    || applyOperationForTask2(nums.minus(nums.first()), newResultB, expectedResult)
                    || applyOperationForTask2(nums.minus(nums.first()), newResultC, expectedResult)
        }
    }
}
