import java.util.Queue
import java.util.LinkedList

val joltageRatings = generateSequence(::readLine).map { it.toLong() }.toList()

fun solve1(joltageRatings: List<Long>): Long {
  val maxJoltage = joltageRatings.maxOrNull() ?: 0L
  var oneJDiff = 0L
  var threeJDiff = 0L
  val allJoltages = listOf(0L) + joltageRatings + (maxJoltage + 3L)
  allJoltages.sorted().windowed(2).forEach {
    when (it[1] - it[0]) {
      1L -> oneJDiff += 1
      3L -> threeJDiff += 1
      else -> throw  Exception("Bad input ${it}")
    }
  }

  return oneJDiff * threeJDiff
}

fun solve2(joltageRatings: List<Long>): Long {
  val allJoltages = listOf(0L) + joltageRatings
  val elDiffs = allJoltages.sorted().windowed(2).map { it[1] - it[0] }
  val groupedDiffs = elDiffs.drop(1).fold(mutableListOf(mutableListOf(elDiffs.first()))) { acc, item ->
    val lastElem = acc.last().last()
    if (lastElem == item) {
      acc.last().add(item)
    } else {
      acc.add(mutableListOf(item))
    }

    acc
  }

  val seqCalc = sequence {
    yield(1L)
    yield(2L)
    yield(4L)
    val triple: Queue<Long> = LinkedList<Long>(listOf(1L, 2L, 4L))
    while (true) {
      val next = triple.sum()
      yield(next)
      triple.remove()
      triple.add(next)
    }
  }

  val result = groupedDiffs.fold(1L) { acc, item ->
    var res = acc

    if (item.first() == 1L) {
      res *= seqCalc.elementAt(item.size - 1)
    }

    res
  }

  return result
}

println(solve1(joltageRatings))
println(solve2(joltageRatings))