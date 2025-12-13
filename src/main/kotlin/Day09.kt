import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private data class Point(
    val x: Int,
    val y: Int,
)

// after eons of iterations this is what my chicken brain was able to come up with (it's pretty fast!)
private fun calculateFilledTileRanges(
    tiles: List<Point>
): HashMap<Int, IntRange> {
    val hashMap = hashMapOf<Point, Int>()
    val ranges = hashMapOf<Int, IntRange>()
    val points = hashSetOf<Point>()

    val lineLength = tiles.maxOf { it.x } + 1
    val linesCount = tiles.maxOf { it.y } + 1

    // fill lines
    for (line in 0 until linesCount) {
        if (tiles.none { it.y == line }) {
            continue
        }

        val (start, end) = tiles
            .filter { it.y == line }
            .map { it.x }

        for (x in start until end + 1) {
            hashMap[Point(x, line)] = line
            points.add(Point(x, line))
        }
    }

    // fill columns
    for (column in 0 until lineLength) {
        if (tiles.none { it.x == column }) {
            continue
        }

        val (start, end) = tiles
            .sortedBy { it.y }
            .filter { it.x == column }
            .map { it.y }

        for (y in start until end + 1) {
            hashMap[Point(column, y)] = y
            points.add(Point(column, y))
        }
    }

    // only go through lines that have points
    for ((index, line) in hashMap.entries.groupBy { it.value }) {
        val start = line.minOf { it.key.x }
        val end = line.maxOf { it.key.x }

        ranges[index] = IntRange(start, end)
    }

    return ranges
}

private fun part1(input: List<String>): Long {
    val locations = input.map {
        it.split(',').map { pos -> pos.toLong() }
    }

    val rectSizes = locations.flatMapIndexed { i, loc ->
        locations.drop(i + 1).map {
            val height = abs(loc[0] - it[0]) + 1
            val length = abs(loc[1] - it[1]) + 1
            height * length
        }
    }

    return rectSizes.max()
}

private fun part2(input: List<String>): Long {
    val redTiles = input.map {
        it.split(',').let { point ->
            Point(point.first().toInt(), point.last().toInt())
        }
    }

    val filledTiles = calculateFilledTileRanges(redTiles)

    var largestRectSize = 0L

    val combinations = mutableListOf<Pair<Point,Point>>()
    redTiles.sortedBy { it.x }.forEachIndexed { i, point1 ->
        redTiles.sortedBy { it.x }.drop(i + 1).forEach { point2 ->
            combinations.add(Pair(point1, point2))
        }
    }

    // this was pain in the ass to optimize, still could be better but... good enough
    combinations.forEach { pair ->
        val firstLine = min(pair.first.y, pair.second.y)
        val lastLine = max(pair.first.y, pair.second.y)
        val xRange = IntRange(pair.first.x, pair.second.x)
        val yRange = IntRange(firstLine, lastLine)

        // check if first and last line contain all x-axis points
        if (xRange.any { it !in filledTiles[firstLine]!! || it !in filledTiles[lastLine]!!})
            return@forEach
        // check if y-axis point is in the filled line
        if (yRange.any { it !in filledTiles[it]!!})
            return@forEach

        val width = abs(pair.first.x - pair.second.x) + 1L
        val height = abs(pair.first.y - pair.second.y) + 1L
        val size = height * width
        if (largestRectSize < size)
            largestRectSize = size
    }

    return largestRectSize
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day09.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}