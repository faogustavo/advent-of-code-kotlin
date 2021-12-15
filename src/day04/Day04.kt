package day04

import readInput
import toIntList

fun main() {
    val input = readInput("day04")

    val drawnNumbers = input.first().split(",").toIntList()
    val boards = input.drop(1)
        .filterNot { it.isEmpty() }
        .map { it.parseAsBoard() }
        .chunked(5)
        .map { Board(it.toNumberList()) }

    println(firstPart(drawnNumbers, boards))
    println(secondPart(drawnNumbers, boards))
}

private fun firstPart(drawnNumbers: List<Int>, boards: List<Board>) =
    recursiveFirstPart(drawnNumbers, boards)

private fun recursiveFirstPart(
    drawnNumbers: List<Int>,
    boards: List<Board>,
    index: Int = 0,
): Int {
    boards.find { it.hasWon }
        ?.let { return it.unmarkedSum * drawnNumbers[index - 1] }

    val draw = drawnNumbers[index]
    return recursiveFirstPart(
        drawnNumbers = drawnNumbers,
        boards = boards.map { it.copy(draw) },
        index = index + 1,
    )
}

private fun secondPart(drawnNumbers: List<Int>, boards: List<Board>) =
    recursiveSecondPart(drawnNumbers, boards)

private fun recursiveSecondPart(
    drawnNumbers: List<Int>,
    boards: List<Board>,
    index: Int = 0,
): Int {
    val draw = drawnNumbers[index]
    val markedBoards = boards.map { it.copy(draw) }

    if (markedBoards.all { it.hasWon }) {
        return markedBoards.last().unmarkedSum * draw
    }

    return recursiveSecondPart(
        drawnNumbers = drawnNumbers,
        boards = markedBoards.filterNot { it.hasWon },
        index = index + 1,
    )
}

private data class Board(
    val numbers: List<List<Number>>,
) {
    val hasWon: Boolean
        get() {
            for (i in 0..4) {
                if (numbers.row(i).all { it.marked }) {
                    return true
                }

                if (numbers.column(i).all { it.marked }) {
                    return true
                }
            }

            return false
        }

    val unmarkedSum: Int
        get() = numbers.flatten()
            .filter { !it.marked }
            .sumOf { it.value }

    fun copy(withDrawnNumber: Int) = copy(
        numbers = numbers.map { row ->
            row.map { number -> number.mark(withDrawnNumber) }
        }
    )
}

private data class Number(
    val value: Int,
    val marked: Boolean = false,
) {
    fun mark(value: Int) = if (value == this.value) {
        copy(marked = true)
    } else {
        this
    }
}

private fun <T> List<List<T>>.row(number: Int): List<T> = this[number]
private fun <T> List<List<T>>.column(number: Int): List<T> = map { it[number] }

private fun String.parseAsBoard() = split(" ")
    .filterNot { it.isEmpty() }
    .toIntList()

private fun List<List<Int>>.toNumberList() = map { row ->
    row.map { Number(it) }
}
