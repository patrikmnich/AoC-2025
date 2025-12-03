private fun findNextMax(input: String, target: Int, slotsReserved: Int = 0): Int {
    if (!input.contains(target.toString()))
        return findNextMax(input, target - 1, slotsReserved)

    if (slotsReserved > 0 && input.indexOf(target.toString()) > input.indices.last - slotsReserved)
        return findNextMax(input, target - 1, slotsReserved)

    return target
}

private fun part1(input: List<String>): Long {
    var result = 0L

    for (line in input) {
        val first = findNextMax(line, 9, slotsReserved = 1)

        val second = findNextMax(
            line.substringAfter(first.toString()),
            9
        )

        result += "$first$second".toInt()
    }

    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L

    val targetSize = 12
    for (line in input) {
        var batteries = ""
        var currentLine = line

        while (batteries.length < targetSize) {
            val nextMax = findNextMax(
                currentLine,
                9,
                slotsReserved = targetSize - batteries.length - 1).toString()

            currentLine = currentLine.substringAfter(nextMax)
            batteries += nextMax
        }
        result += batteries.toLong()
    }

    return result
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day03.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}