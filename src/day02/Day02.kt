package day02

import readInput

fun main() {
    fun partOne(data: List<Pair<String, Int>>): Int =
        data.fold(StatePartOne()) { acc, movement -> acc.copy(movement) }.pos

    fun partTwo(data: List<Pair<String, Int>>): Int =
        data.fold(StatePartTwo()) { acc, movement -> acc.copy(movement) }.pos

    val finalState = readInput("day02")
        .map { it.parseAsMovement() }

    println(partOne(finalState))
    println(partTwo(finalState))
}

private data class StatePartOne(
    val hPos: Int = 0,
    val vPos: Int = 0,
) {
    val pos: Int
        get() = hPos * vPos

    fun copy(movement: Pair<String, Int>): StatePartOne = when {
        movement.first.isForward -> copy(hPos = hPos + movement.second)
        movement.first.isDown -> copy(vPos = vPos + movement.second)
        movement.first.isUp -> copy(vPos = vPos - movement.second)
        else -> this
    }
}

private data class StatePartTwo(
    val hPos: Int = 0,
    val aimPos: Int = 0,
    val depth: Int = 0,
) {
    val pos: Int
        get() = hPos * depth

    fun copy(movement: Pair<String, Int>): StatePartTwo = when {
        movement.first.isForward -> copy(
            hPos = hPos + movement.second,
            depth = depth + (movement.second * aimPos)
        )
        movement.first.isDown -> copy(aimPos = aimPos + movement.second)
        movement.first.isUp -> copy(aimPos = aimPos - movement.second)
        else -> this
    }
}

private val String.isForward: Boolean
    get() = "forward".equals(this, true)

private val String.isUp: Boolean
    get() = "up".equals(this, true)

private val String.isDown: Boolean
    get() = "down".equals(this, true)

private fun String.parseAsMovement(): Pair<String, Int> =
    split(' ').let { input ->
        input.first() to input.last().toInt()
    }
