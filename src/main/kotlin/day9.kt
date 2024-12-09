package org.example

import java.nio.file.Files
import java.nio.file.Paths
import java.util.LinkedList
import java.util.Queue

fun main() {
    val path = Paths.get("src", "main", "resources", "day9.txt")
    val input = Files.readString(path).trim()
    val day9 = Day9()
    val solution1 = day9.solveTask1(input)
    println("Solution 1: $solution1")
    val solution2 = day9.solveTask2(input)
    println("Solution 2: $solution2")
}

class Day9 {

    fun solveTask1(input: String): Long {
        val takenToFree = getTakenToFree(input)
        val memoryLayout = getMemoryLayout(takenToFree)
        val compactedMemoryLayout = compactMemoryLayout(memoryLayout)
        return calculateChecksum(compactedMemoryLayout)
    }

    fun solveTask2(input: String): Long {
        val takenToFree = getTakenToFree(input)
        val memoryLayout = getMemoryLayoutTask2(takenToFree)
        val compactedMemoryLayout = compactMemoryLayoutTask2(memoryLayout)
        return calculateChecksumTask2(compactedMemoryLayout)
    }

    private fun getTakenToFree(input: String): Pair<Queue<Int>, Queue<Int>> {
        val taken = LinkedList<Int>()
        val free = LinkedList<Int>()
        for ((idx, c) in input.withIndex()) {
            val charAsInt = c.toString().toInt()
            if (idx % 2 == 0) {
                taken.add(charAsInt)
            } else {
                free.add(charAsInt)
            }
        }
        return taken to free
    }

    private fun getMemoryLayout(takenToFree: Pair<Queue<Int>, Queue<Int>>): List<String> {
        val taken = takenToFree.first
        val free = takenToFree.second
        return buildList {
            var i = 0
            while (taken.isNotEmpty() || free.isNotEmpty()) {
                if (taken.isNotEmpty()) {
                    val takenValue = taken.poll()
                    for (j in 0..<takenValue) {
                        add(i.toString())
                    }
                }
                if (free.isNotEmpty()) {
                    val freeValue = free.poll()
                    for (j in 0..<freeValue) {
                        add(".")
                    }
                }
                i++
            }
        }
    }

    private fun compactMemoryLayout(memoryLayout: List<String>): List<Int> {
        return buildList {
            var freeIdx = memoryLayout.indexOf(".")
            var takenIdx = memoryLayout.size - 1
            var currentIdx = 0
            while (true) {
                if (currentIdx < freeIdx) {
                    add(memoryLayout[currentIdx].toInt())
                }
                if (currentIdx == freeIdx) {
                    add(memoryLayout[takenIdx].toInt())
                    freeIdx = memoryLayout.subList(freeIdx + 1, memoryLayout.size - 1).indexOf(".") + freeIdx + 1
                    takenIdx = getNextTakenIndex(takenIdx, memoryLayout)
                }
                if (freeIdx > takenIdx) {
                    while (currentIdx <= takenIdx) {
                        if (memoryLayout[currentIdx] != ".") {
                            add(memoryLayout[currentIdx].toInt())
                        }
                        currentIdx++
                    }
                    break
                }
                currentIdx++
            }
        }
    }

    private fun getNextTakenIndex(currentTakenIdx: Int, memoryLayout: List<String>): Int {
        var newIdx = currentTakenIdx
        do {
            newIdx--
        } while (memoryLayout[newIdx] == ".")
        return newIdx
    }

    private fun calculateChecksum(compactedLayout: List<Int>): Long {
        var result = 0L
        for ((i, num) in compactedLayout.withIndex()) {
            result += i * num
        }
        return result
    }

    private fun getMemoryLayoutTask2(takenToFree: Pair<Queue<Int>, Queue<Int>>): List<File> {
        val taken = takenToFree.first
        val free = takenToFree.second
        return buildList {
            var i = 0
            while (taken.isNotEmpty() || free.isNotEmpty()) {
                if (taken.isNotEmpty()) {
                    val takenValue = taken.poll()
                    val file = File(takenValue, i)
                    add(file)
                }
                if (free.isNotEmpty()) {
                    val freeValue = free.poll()
                    val file = File(freeValue, -1)
                    add(file)
                }
                i++
            }
        }
    }

    private fun compactMemoryLayoutTask2(files: List<File>): List<File> {
        var idx = files.size - 1
        val mutableFiles = files.toMutableList()
        while (idx != 0) {
            val fileToMove = mutableFiles[idx]
            if (fileToMove.value == -1) {
                idx--
                continue
            }
            val freeSpace = mutableFiles.firstOrNull { it.size >= fileToMove.size && it.value == -1 }
            val freeSpaceIndex = mutableFiles.indexOf(freeSpace)
            if (freeSpace != null && freeSpaceIndex < idx) {
                val replacement = File(fileToMove.size, -1)
                mutableFiles.add(idx + 1, replacement)
                mutableFiles.removeAt(idx)
                mutableFiles.removeAt(freeSpaceIndex)
                mutableFiles.add(freeSpaceIndex, fileToMove)
                val takenSpace = fileToMove.size
                val newFreeSpace = File(freeSpace.size - takenSpace, -1)
                if (newFreeSpace.size > 0) {
                    mutableFiles.add(freeSpaceIndex + 1, newFreeSpace)
                }
            }
            idx--
        }
        return mutableFiles
    }

    private fun calculateChecksumTask2(files: List<File>): Long {
        var result = 0L
        var idx = 0
        for (file in files) {
            if (file.value == -1) {
                idx += file.size
                continue
            }
            for (i in 0..<file.size) {
                result += idx * file.value
                idx++
            }
        }
        return result
    }

    private data class File(
        val size: Int,
        val value: Int
    )
}
