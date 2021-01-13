val timestampToDepart = readLine()!!.toLong()
val busIds = readLine()!!.split(',')

fun solve1(timestampToDepart: Long, busIds: List<String>): Long {
  val busNums = busIds.filter { it != "x" }.map { it.toLong() }

  val nearestArrives = busNums.map { Pair(it, it - timestampToDepart % it) }.sortedBy { it.component2() }

  return nearestArrives.first().let { (bus, minutes) -> bus * minutes }
}

fun solve2(busIds: List<String>): Long {
  val busNums = busIds.withIndex().filter { it.value != "x" }.map { Pair(it.index.toLong(), it.value.toLong()) }
  var lastSyncPoint = 0L
  val firstBus = busNums.first().second
  var periodMultiplier = firstBus

  busNums.drop(1).forEach { (curIndex, currentNum) ->
    while ((lastSyncPoint + curIndex) % currentNum != 0L) {
      lastSyncPoint += periodMultiplier
    }
    periodMultiplier *= currentNum

  }

  return lastSyncPoint
}

println(solve1(timestampToDepart, busIds))
println(solve2(busIds))