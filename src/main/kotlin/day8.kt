package org.example

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("src", "main", "resources", "day8.txt")
    val lines = Files.readAllLines(path).filterNot { it.isBlank() }
    val day8 = Day8()
    val solution1 = day8.solveTask1(lines)
    println("Solution 1: $solution1")
    val solution2 = day8.solveTask2(lines)
    println("Solution 2: $solution2")
}

class Day8 {

    fun solveTask1(lines: List<String>): Int {
        val uniqueLocations = mutableSetOf<Pair<Int, Int>>()
        val antennas = findAllAntennas(lines)
        for (antenna in antennas) {
            val locations = antenna.value
            for (i in 0..<locations.size) {
                for (j in i + 1..<locations.size) {
                    val location1 = locations[i]
                    val location2 = locations[j]
                    val iDiff = location1.first - location2.first
                    val jDiff = location1.second - location2.second
                    val newLocation1 = location1.first + iDiff to location1.second + jDiff
                    val newLocation2 = location2.first - iDiff to location2.second - jDiff
                    if (isWithinMapBounds(newLocation1, lines)) {
                        uniqueLocations.add(newLocation1)
                    }
                    if (isWithinMapBounds(newLocation2, lines)) {
                        uniqueLocations.add(newLocation2)
                    }
                }
            }
        }
        return uniqueLocations.size
    }

    fun solveTask2(lines: List<String>): Int {
        val uniqueLocations = mutableSetOf<Pair<Int, Int>>()
        val antennas = findAllAntennas(lines)
        for (antenna in antennas) {
            val locations = antenna.value
            for (i in 0..<locations.size) {
                for (j in i + 1..<locations.size) {
                    val location1 = locations[i]
                    val location2 = locations[j]
                    val iDiff = location1.first - location2.first
                    val jDiff = location1.second - location2.second
                    val locations1 = findLocationsUntilOutOfBounds1(location1, iDiff, jDiff, lines)
                    val locations2 = findLocationsUntilOutOfBounds2(location2, iDiff, jDiff, lines)
                    uniqueLocations.addAll(locations1)
                    uniqueLocations.addAll(locations2)
                }
            }
        }
        return uniqueLocations.size
    }

    private fun findAllAntennas(lines: List<String>): Map<Char, List<Pair<Int, Int>>> {
        val antennaToLocation = mutableMapOf<Char, List<Pair<Int, Int>>>()
        for ((i, line) in lines.withIndex()) {
            for ((j, c) in line.withIndex()) {
                if (c != '.') antennaToLocation.compute(c) { _, v -> v?.plus(i to j) ?: listOf(i to j) }
            }
        }
        return antennaToLocation
    }

    private fun findLocationsUntilOutOfBounds1(location: Pair<Int, Int>, iDiff: Int, jDiff: Int, lines: List<String>): List<Pair<Int, Int>> {
        val list = mutableListOf(location)
        val newLocation = location.first + iDiff to location.second + jDiff
        if (!isWithinMapBounds(newLocation, lines)) {
            return list
        }
        list.add(newLocation)
        list.addAll(findLocationsUntilOutOfBounds1(newLocation, iDiff, jDiff, lines))
        return list
    }

    private fun findLocationsUntilOutOfBounds2(location: Pair<Int, Int>, iDiff: Int, jDiff: Int, lines: List<String>): List<Pair<Int, Int>> {
        val list = mutableListOf(location)
        val newLocation = location.first - iDiff to location.second - jDiff
        if (!isWithinMapBounds(newLocation, lines)) {
            return list
        }
        list.add(newLocation)
        list.addAll(findLocationsUntilOutOfBounds2(newLocation, iDiff, jDiff, lines))
        return list
    }

    private fun isWithinMapBounds(location: Pair<Int, Int>, lines: List<String>): Boolean {
        val i = location.first
        val j = location.second
        val maxI = lines.size - 1
        val maxJ = lines.first().length - 1
        return i >= 0 && j >= 0 && i <= maxI && j <= maxJ
    }
}
