import kotlin.math.abs

private fun part1(input: List<String>): Int {
    var result = 0
    var point = 50

    input.forEach { line ->
        val direction = line.take(1)
        val amount = line.drop(1).toInt()

        // don't care about overflows here, just check modulo
        point = if (direction == "L") {
            (point - amount) % 100
        } else {
            (point + amount) % 100
        }

        if (point == 0)
            result += 1
    }
    return result
}

private fun part2(input: List<String>): Int {
    var result = 0
    var point = 50

    input.forEach { line ->
        val direction = line.take(1)
        val amount = line.drop(1).toInt()
        var currentAmount = amount
        var pointZeroCounter = 0

        // just loop by 1, keep track of passing the "edges", update counters and reset after
        while (currentAmount > 0) {
            point = if (direction == "L") {
                point - 1
            } else {
                point + 1
            }

            if (point == 0 || point == 100)
                pointZeroCounter += 1
            if (point == -1)
                point = 99
            if (point == 100)
                point = 0

            currentAmount -= 1
        }

        result += pointZeroCounter
    }
    return result
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day01.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}