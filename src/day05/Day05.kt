package day05

import readInput

fun main() {
    val input = readInput("day05")

    println(firstPart(input))
}

private fun firstPart(input: List<String>): Int {
    val seaMap = mutableMapOf<Pair<Int, Int>, Int>()

    input.map { Vent(it) }
        .onEach(::println)
        .flatMap { it.ventRange }
        .forEach { vent -> seaMap += vent }

    return seaMap
        .values
        .count { it >= 2 }
}

private class Vent(private val line: String) {
    private val _ranges by lazy {
        line.split("->")
            .map(::parseInterval)
            .take(2)
            .let { it.first() to it.last() }
    }

    val isValid: Boolean by lazy {
        _ranges.first.first == _ranges.second.first ||
                _ranges.first.second == _ranges.second.second
    }

    val ventRange: List<Pair<Int, Int>> by lazy {
        val output = mutableListOf<Pair<Int, Int>>()

        val start = _ranges.first
        val end = _ranges.second

        when {
            start.first == end.first -> {
                for (i in start.second rangeTo end.second) {
                    output += start.first to i
                }
            }
            start.second == end.second -> {
                for (i in start.first rangeTo end.first) {
                    output += i to start.second
                }
            }
            else -> {
                val iList = (start.first rangeTo end.first).toList()
                val jList = (start.second rangeTo end.second).toList()

                iList.zip(jList)
                    .forEach { output += it }
            }
        }

        output
    }

    override fun toString(): String {
        return "[$line] -> $ventRange"
    }

    private fun parseInterval(interval: String): Pair<Int, Int> =
        interval.trim().split(",")
            .take(2)
            .let { it.first().toInt() to it.last().toInt() }
}

private infix fun Int.rangeTo(other: Int): IntProgression = if (this > other) {
    this downTo other
} else {
    this .. other
}

private operator fun <K> MutableMap<K, Int>.plusAssign(key: K) {
    this[key] = (this[key] ?: 0).inc()
}
