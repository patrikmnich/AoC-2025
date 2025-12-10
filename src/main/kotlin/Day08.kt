import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.text.toDouble

private data class JunctionBox(
    val x: Double,
    val y: Double,
    val z: Double,
    var isPartOfCircuit: Boolean = false
)

private data class EuclideanDistance(
    val first: JunctionBox,
    val second: JunctionBox,
    val distance: Double,
)

private fun String.toJunctionBox(): JunctionBox = this.split(',').let {
    JunctionBox(
        it[0].toDouble(),
        it[1].toDouble(),
        it[2].toDouble(),
    )
}

private fun calculateEuclideanDistance(p: JunctionBox, q: JunctionBox): Double {
    return sqrt(
        ((p.x - q.x).pow(2.0) +
         (p.y - q.y).pow(2.0) +
         (p.z - q.z).pow(2.0))
    )
}

private fun connectBoxes(input: List<String>, limit: Int? = null): Long {
    val boxes = input.map { it.toJunctionBox() }
    val circuits = boxes.map { mutableListOf(it) }.toMutableList()

    val distances = boxes.flatMapIndexed { i, first ->
        boxes.drop(i+1). map { second ->
            EuclideanDistance(
                first,
                second,
                calculateEuclideanDistance(first, second)
            )
        }
    }

    distances.sortedBy { it.distance }.take(limit ?: distances.size).forEach { distance ->
        if (circuits.any { it.contains(distance.first) && it.contains(distance.second) }) {
            //println("boxes are in the same circuit already")
            return@forEach
        }

        if (!distance.first.isPartOfCircuit && !distance.second.isPartOfCircuit) {
            //println("none of the boxes are part of circuit, create new")
            circuits.add(mutableListOf(distance.first, distance.second))
            circuits.remove(listOf(distance.first))
            circuits.remove(listOf(distance.second))
            distance.first.isPartOfCircuit = true
            distance.second.isPartOfCircuit = true
            return@forEach
        }

        if (distance.first.isPartOfCircuit && distance.second.isPartOfCircuit) {
            //println("boxes are in different circuits")
            val c1 = circuits.find { it.contains(distance.first) }!!
            val c2 = circuits.find { it.contains(distance.second) }!!
            circuits.remove(c1)
            circuits.remove(c2)
            circuits.add((c1 + c2).toMutableList())
            return@forEach
        }

        if (distance.first.isPartOfCircuit) {
            //println("first box already in circuit")
            circuits.find { it.contains(distance.first) }!!
                .add(distance.second)
                .also { distance.second.isPartOfCircuit = true }
                .also { circuits.remove(listOf(distance.second)) }
        } else if (distance.second.isPartOfCircuit) {
            //println("second box already in circuit")
            circuits.find { it.contains(distance.second) }!!
                .add(distance.first)
                .also { distance.first.isPartOfCircuit = true }
                .also { circuits.remove(listOf(distance.first)) }
        }

        if (circuits.size == 1 && limit == null) {
            return (distance.first.x * distance.second.x).toLong()
        }

    }

    return circuits.sortedByDescending { it.size }.take(3).map { it.size.toLong() }.reduce(Long::times)
}

private fun part1(input: List<String>): Long {
    return connectBoxes(input, limit = 10) // change to 1000 for real input
}

private fun part2(input: List<String>): Long {
    return connectBoxes(input)
}

private fun main() {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream("day08.txt")

    val input: List<String> = inputStream.bufferedReader().readLines()

    println(part1(input))
    println(part2(input))
}