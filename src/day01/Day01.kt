package day01

import readInput
import toIntList

fun main() {
    fun part1(input: List<Int>): Int =
        input.windowed(2)
            .count { (left, right) -> left < right }

    fun part2(input: List<Int>): Int =
        input.windowed(4)
            .count { (left, _, _, right) -> left < right }

    val input = readInput("day01").toIntList()
    println(part1(input))
    println(part2(input))
}
