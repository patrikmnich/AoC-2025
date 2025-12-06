private fun part1(input: List<String>): Long {
    var result = 0L

    val symbols = input.last().split(" ")
        .filter { it.isNotBlank() }

    val rows = input.dropLast(1).map { line ->
        line.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }
    }

    for (i in 0 until symbols.size) {
        val column = mutableListOf<Long>()
        rows.forEach { numbers ->
            column.add(numbers[i])
        }

        result += if (symbols[i] == "+")
            column.sum()
        else
            column.reduce(Long::times)
    }

    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L

    val symbols = input.last().split(" ")
        .filter { it.isNotBlank() }

    // pad numbers with 'x' from correct side and remove it later when building numbers
    val xPaddedRows = mutableListOf<List<String>>()
    input.dropLast(1).forEach { line ->
        val paddedLine = StringBuilder()

        for (i in 0 until line.length) {
            // if char is not empty, add it immediately
            if (!line[i].isWhitespace()) {
                paddedLine.append(line[i])
            } else {
                // if any char in given column is not empty, i can consider it
                // "within number range" so pad with x here, otherwise append whitespace
                if (input.any { row -> !row[i].isWhitespace()}) {
                    paddedLine.append('x')
                } else paddedLine.append(line[i])
            }
        }
        xPaddedRows.add(paddedLine.split(" "))
    }

    for (i in 0 until symbols.size) {
        val column = mutableListOf<String>()
        xPaddedRows.forEach { numbers ->
            column.add(numbers[i])
        }

        val numbers = mutableListOf<Long>()

        for (j in 0 until column.first().length) {
            val number = StringBuilder()
            for (row in column.indices) {
                if (column[row][j] != 'x')
                    number.append(column[row][j])
            }
            numbers.add(number.toString().toLong())
        }

        result += if (symbols[i] == "+")
            numbers.sum()
        else
            numbers.reduce(Long::times)
    }

    return result
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day06.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}