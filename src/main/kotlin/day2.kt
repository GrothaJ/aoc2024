package org.example

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("src", "main", "resources", "day2.txt")
    val lines = Files.readAllLines(path)
        .filterNot { it.isBlank() }
    // task 1
    var result1 = 0
    for (line in lines) {
        val nums = line.split(" ")
            .map { Integer.valueOf(it) }
        if (isOkay(nums)) {
            result1++
        }
    }
    println(result1)

    // task 2
    var result2 = 0
    for (line in lines) {
        val nums = line.split(" ")
            .map { Integer.valueOf(it) }
        val isOk = isOkay(nums)
        if (isOk) {
            result2++
        } else {
            var newIsOk = false
            for (idx in nums.indices) {
                val newList = nums.toMutableList()
                newList.removeAt(idx)
                if (isOkay(newList)) {
                    newIsOk = true
                }
            }
            if (newIsOk) {
                result2++
            } else {
                println("$nums is never ok")
            }
        }
    }
    println(result2)
}

fun isOkay(nums: List<Int>): Boolean {
    var isOkayAsc = true
    var isOkayDesc = true
    for (idx in 1..<nums.size) {
        if (nums[idx] <= nums[idx - 1]) isOkayAsc = false
        if (nums[idx] - nums[idx - 1] > 3) isOkayAsc = false
    }
    for (idx in 1..<nums.size) {
        if (nums[idx] >= nums[idx - 1]) isOkayDesc = false
        if (nums[idx - 1] - nums[idx] > 3) isOkayDesc = false
    }
    return isOkayAsc || isOkayDesc
}
