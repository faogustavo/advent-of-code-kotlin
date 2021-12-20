package day06

import readInput
import toIntList
import java.util.*

fun main() {
    val input = readInput("day06").first()

    println(firstPart(input, 80))
    println(firstPart(input, 256))
}

private fun firstPart(data: String, days: Int): Long {
    val fishes: Map<Int, Long> = data.split(",")
        .toIntList()
        .groupBy { it }
        .map { it.key to it.value.size.toLong() }
        .toMap()

    return (1..days).toList()
        .fold(fishes) { acc, _ -> acc.nextRound() }
        .values
        .sum()
}

private fun Map<Int, Long>.nextRound(): Map<Int, Long> = mapOf(
    0 to this.getOrZero(1),
    1 to this.getOrZero(2),
    2 to this.getOrZero(3),
    3 to this.getOrZero(4),
    4 to this.getOrZero(5),
    5 to this.getOrZero(6),
    6 to this.getOrZero(7) + this.getOrZero(0),
    7 to this.getOrZero(8),
    8 to this.getOrZero(0),
)

private fun <K> Map<K, Long>.getOrZero(key: K): Long = getOrElse(key) { 0 }

