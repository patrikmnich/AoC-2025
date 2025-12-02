
private fun part1(input: List<String>): Long {
    var result = 0L

    val ranges = mutableListOf<List<String>>()
    input.first().split(",").forEach {
        it.split("-").let { list -> ranges.add(list) }
    }

    for (range in ranges) {
        for (i in range.first().toLong() .. range.last().toLong()) {
            for (size in 1 until i.toString().length) {
                val chunked = i.toString().chunked(size)
                if (chunked.size == 2 && chunked.all { it == chunked[0] }) {
                    result += i
                }
            }
        }
    }

    return result
}

private fun part2(input: List<String>): Long {
    var result = 0L

    val ranges = mutableListOf<List<String>>()
    input.first().split(",").forEach {
        it.split("-").let { list -> ranges.add(list) }
    }

    val checked = mutableListOf<Long>()
    for (range in ranges) {
        for (i in range.first().toLong() .. range.last().toLong()) {
            for (size in 1 until i.toString().length) {
                val chunked = i.toString().chunked(size)
                if (chunked.all { it == chunked[0] }) {
                    if (!checked.contains(i)) {
                        result += i
                        checked.add(i)
                    }
                }
            }
        }
    }

    return result
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day02.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}