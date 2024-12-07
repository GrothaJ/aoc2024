package org.example

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.floor

fun main() {
    val path = Paths.get("src", "main", "resources", "day5.txt")
    val lines = Files.readAllLines(path)
    val day5 = Day5()
    val solution1 = day5.solveTask1(lines)
    println("Solution 1: $solution1")
    val solution2 = day5.solveTask2(lines)
    println("Solution 2: $solution2")
}

class Day5 {

    fun solveTask1(lines: List<String>): Int {
        var result = 0
        val rulesToPages = getRulesToPages(lines)
        val rules = rulesToPages.first
        val pages = rulesToPages.second
        for (page in pages) {
            if (isPageOkay(page, rules)) {
                val middleNumber = page[floor(page.size.toDouble() / 2).toInt()]
                result += middleNumber
            }
        }
        return result
    }

    fun solveTask2(lines: List<String>): Int {
        var result = 0
        val rulesToPages = getRulesToPages(lines)
        val rules = rulesToPages.first
        val pages = rulesToPages.second
        // get incorrect pages
        val incorrectPages = mutableListOf<List<Int>>()
        for (page in pages) {
            if (!isPageOkay(page, rules)) {
                incorrectPages.add(page)
            }
        }
        // sort
        val sortedPages = mutableListOf<List<Int>>()
        for (page in incorrectPages) {
            val sortedPage = sortPage(page, rules)
            sortedPages.add(sortedPage)
        }
        for (page in sortedPages) {
            val middleNumber = page[floor(page.size.toDouble() / 2).toInt()]
            result += middleNumber
        }
        return result
    }

    private fun getRulesToPages(lines: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val rules = mutableMapOf<Int, List<Int>>()
        val pages = mutableListOf<List<Int>>()
        var isRule = true
        // setup
        for (line in lines) {
            if (line.isBlank()) isRule = false
            if (isRule) {
                val nums = line.split("|")
                val before = nums[0].toInt()
                val after = nums[1].toInt()
                rules.compute(after) { _, v -> if (v == null) listOf(before) else v + before }
            } else if (line.isNotBlank()) {
                pages.add(line.split(",").map { it.toInt() })
            }
        }
        return rules to pages
    }

    private fun isPageOkay(page: List<Int>, rules: Map<Int, List<Int>>): Boolean {
        for (idx in page.indices) {
            val element = page[idx]
            val rulesForElement = rules[element] ?: continue
            for (rule in rulesForElement) {
                if (page.indexOf(rule) > page.indexOf(element)) return false
            }
        }
        return true
    }

    private fun sortPage(page: List<Int>, rules: Map<Int, List<Int>>): List<Int> {
        val newPage = constructNewPage(page, rules)
        if (isPageOkay(newPage, rules)) {
            return newPage
        }
        return sortPage(newPage, rules)
    }

    private fun constructNewPage(page: List<Int>, rules: Map<Int, List<Int>>): List<Int> {
        for (element in page) {
            val rulesForElement = rules[element] ?: continue
            for (rule in rulesForElement) {
                if (page.indexOf(rule) > page.indexOf(element)) {
                    return swap(rule, element, page)
                }
            }
        }
        return page
    }

    private fun swap(element1: Int, element2: Int, page: List<Int>): List<Int> {
        return buildList {
            for (element in page) {
                when (element) {
                    element1 -> this.add(element2)
                    element2 -> this.add(element1)
                    else -> this.add(element)
                }
            }
        }
    }
}
