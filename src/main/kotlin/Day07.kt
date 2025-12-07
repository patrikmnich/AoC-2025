private fun part1(input: List<String>): Long {
    var result = 0L

    val beamIndices = mutableSetOf(input.first().indexOf('S'))
    for (line in input.drop(1)) {
        for (i in 0 until line.length) {
            if (line[i] == '^') {
                if (!beamIndices.contains(i))
                    continue

                beamIndices.apply {
                    remove(i)
                    add(i+1)
                    add(i-1)
                }

                result += 1
            }
        }
    }
    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L
    val lineMap = mutableMapOf(input.first().indexOf('S') to 1L)

    var currentLine = 0
    for (line in input.drop(1)) {
        currentLine += 1
        if (!line.contains('^'))
            continue

        for (i in line.indices) {
            if (line[i] == '^') {

                lineMap[i-1] = (lineMap[i-1] ?: 0) + (lineMap[i] ?: 1)
                lineMap[i+1] = (lineMap[i+1] ?: 0) + (lineMap[i] ?: 1)
                lineMap[i] = 0
            }
        }
    }

    result = lineMap.values.sum()
    return result
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day07.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}