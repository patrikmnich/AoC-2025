fun List<LongRange>.mergeOverlaps(): List<LongRange> {
    if (isEmpty()) return emptyList()

    val sorted = this.sortedBy { it.first }
    val result = mutableListOf<LongRange>()

    var current = sorted.first()

    for (i in 1 until sorted.size) {
        val next = sorted[i]

        if (current.last >= next.first) {
            current = LongRange(current.first, maxOf(current.last, next.last))
        } else {
            result += current
            current = next
        }
    }

    result += current

    return result
}

private fun part1(input: List<String>): Long {
    var result = 0L

    val emptyLine = input.indexOf(input.find { it.isEmpty() })
    val ranges = input.take(emptyLine).map {
        it.split("-").let { pair ->
            LongRange(pair.first().toLong(), pair.last().toLong())
        }
    }
    val ids = input.drop(emptyLine + 1).map { it.toLong() }

    for (id in ids)
        if (ranges.any { it.contains(id) }) result += 1

    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L

    val emptyLine = input.indexOf(input.find { it.isEmpty() })

    val ranges = input.take(emptyLine).map {
        it.split("-").let { pair ->
            LongRange(pair.first().toLong(), pair.last().toLong())
        }
    }

    ranges.mergeOverlaps().forEach {
        result += (it.last + 1) - it.first
    }

    return result

}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day05.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}