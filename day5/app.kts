fun getSeatFromSeatCode(seatCode: String): Int {
    var rowTop = 127
    var rowBottom = 0
    var colTop = 7
    var colBottom = 0
    for (c in seatCode) {
        when(c) {
            'F' -> rowTop -= (rowTop - rowBottom + 1) / 2
            'B' -> rowBottom += (rowTop - rowBottom + 1) / 2
            'L' -> colTop -= (colTop - colBottom + 1) / 2
            'R' -> colBottom += (colTop - colBottom + 1) / 2
        }
    }

    return rowBottom * 8 + colBottom
}

fun solve1(seatCodes: List<String>): Int {
    return seatCodes.map { getSeatFromSeatCode(it) }.maxOrNull() ?: -1
}

fun solve2(seatCodes: List<String>): Int {
    val seats = seatCodes.map { getSeatFromSeatCode(it) }
    val sorted = seats.sorted()

    for (i in 1..sorted.size) {
        if (sorted[i] != sorted[i - 1] + 1) {
            return sorted[i] - 1
        }
    }

    return -1
}

val lines = generateSequence(::readLine).toList()

//println(solve1(lines))
println(solve2(lines))