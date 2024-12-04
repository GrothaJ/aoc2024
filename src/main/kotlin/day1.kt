package org.example

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val path = Paths.get("src", "main", "resources", "day1.txt")
    val input = Files.readAllLines(path)
    val lists = input.map { line ->
        line.split(" ")
            .filterNot { it.isBlank() }
            .map { it.trim() }
            .map { Integer.valueOf(it) }
    }
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    lists.forEach {
        list1.add(it[0])
        list2.add(it[1])
    }

    // task 1
    val sortedList1 = list1.sorted()
    val sortedList2 = list2.sorted()
    var result = 0
    for (i in sortedList1.indices) {
        val element1 = sortedList1[i]
        val element2 = sortedList2[i]
        result += abs(element1 - element2)
    }
    println(result)

    // task 2
    val numberToNumberOfAppearances = mutableMapOf<Int, Int>()
    for (element in list1) {
        numberToNumberOfAppearances[element] = 0
    }
    for (element in list2) {
        val currentValue = numberToNumberOfAppearances[element]
        if (currentValue != null) {
            numberToNumberOfAppearances[element] = currentValue.plus(1)
        }
    }
    var result2 = 0
    for (element in list1) {
        val numberOfAppearances = numberToNumberOfAppearances[element]
        val valueToAdd = element * numberOfAppearances!!
        result2 += valueToAdd
    }
    println(result2)
}
