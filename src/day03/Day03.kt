package day03

import readInput

fun main() {
    val data = readInput("day03")
        .map { row -> row.toCharArray().map { it.digitToInt() } }

    println(partOne(data))
    println(partTwo(data))
}

fun partOne(data: List<List<Int>>): Int {
    val gamma = (0 until data.first().size).map {
        mostCommonBitAtPosition(data, it)
    }
    val epsilon = gamma.map {
        if (it == 1) 0 else 1
    }

    val gammaDec = gamma.joinToString("").toInt(2)
    val epsilonDec = epsilon.joinToString("").toInt(2)

    return gammaDec * epsilonDec
}

fun partTwo(data: List<List<Int>>): Int {
    val oxygen = recursiveFilter(
        data = data,
        idx = 0,
        type = Type.Oxygen
    ).joinToString("").toInt(2)

    val co2 = recursiveFilter(
        data = data,
        idx = 0,
        type = Type.CO2
    ).joinToString("").toInt(2)

    return oxygen * co2
}

tailrec fun recursiveFilter(
    data: List<List<Int>>,
    idx: Int,
    type: Type,
): List<Int> = if (data.size == 1 || idx >= data.first().size) {
    data.first()
} else {
    val mostCommonBit = mostCommonBitAtPosition(data, idx)
    recursiveFilter(
        data = data.filter { digits ->
            type.match(digits[idx], mostCommonBit)
        },
        idx = idx + 1,
        type = type
    )
}

fun mostCommonBitAtPosition(data: List<List<Int>>, position: Int): Int {
    var ones = 0

    data.forEach { binary ->
        if (binary[position] == 1) ones++
    }

    val zeros = data.size - ones
    return if (ones >= zeros) 1 else 0
}

enum class Type {
    CO2,
    Oxygen;

    fun match(left: Int, right: Int): Boolean = when (this) {
        Oxygen -> left == right
        CO2 -> left != right
    }
}
