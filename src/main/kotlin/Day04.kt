const val PAPER_ROLL = '@'
const val SPACE = '.'

private fun isRollAccessible(input: List<String>, x: Int, y: Int): Boolean {
    var adjacentCount = 0

    val directions = listOf(
        x-1 to y-1, x-1 to y, x-1 to y+1,
        x   to y-1,           x   to y+1,
        x+1 to y-1, x+1 to y, x+1 to y+1
    )

    for ((dx, dy) in directions) {
        try {
            if (input[dx][dy] == PAPER_ROLL) {
                adjacentCount += 1
            }
        } catch (_: Exception) { }
    }

    return adjacentCount < 4
}

private fun part1(input: List<String>): Long {
    var result = 0L

    val lineLength = input.first().length

    for (x in 0 until input.size) {
        for (y in 0 until lineLength) {
            if (input[x][y] == SPACE)
                continue

            if (isRollAccessible(input, x, y))
                result += 1

        }
    }
    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L

    var currentInput = input
    val lineLength = input.first().length

    val accessibleRolls = mutableListOf<Pair<Int, Int>>()

    while (true) {
        for (x in 0 until currentInput.size) {
            for (y in 0 until lineLength) {
                if (currentInput[x][y] == SPACE)
                    continue

                if (isRollAccessible(currentInput, x, y)) {
                    accessibleRolls.add(x to y)
                }
            }
        }

        if (accessibleRolls.isEmpty()) {
            return result
        } else {
            result += accessibleRolls.size

            val cleared = mutableListOf<String>()
            currentInput.forEachIndexed { x, line ->
                val clearedLine = StringBuilder()
                for (y in 0 until line.length) {
                    if (line[y] == SPACE)
                        clearedLine.append(SPACE)
                    else
                        clearedLine.append(if (accessibleRolls.contains(x to y)) SPACE else PAPER_ROLL)
                }
                cleared.add(clearedLine.toString())
            }

            currentInput = cleared

            accessibleRolls.clear()
        }
    }
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day04.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}