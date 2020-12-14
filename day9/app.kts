val lines = generateSequence(::readLine).toList()
val nums = lines.map { it.toLong() }

fun hasTwoSum(nums: List<Long>, sum: Long): Boolean {
  val visited = mutableSetOf<Long>()
  nums.forEach {
    if (visited.contains(sum - it)) {
      return true
    } else {
      visited.add(it)
    }
  }

  return false
}

fun solve1(nums: List<Long>, preamble_size: Int = 25): Long {
  return nums.windowed(preamble_size + 1).find { !hasTwoSum(it.dropLast(1), it.last())}!!.last()
}

fun solve2(nums: List<Long>, preamble_size: Int = 25): Long {
  val invalidNum = solve1(nums, preamble_size)

  var lowInd = 0
  var highInd = 0
  var sum = nums[lowInd]
  loop@ while (sum != invalidNum && highInd < nums.size) {
    if (lowInd == highInd) {
      highInd += 1
      sum += nums.getOrElse(highInd) { 0 }
      continue@loop
    }

    if (sum < invalidNum) {
      highInd += 1
      sum += nums.getOrElse(highInd) { 0 }
    } else {
      sum -= nums.getOrElse(lowInd) { 0 }
      lowInd += 1
    }
  }

  val window = nums.slice(lowInd..highInd)

  return window.maxOrNull()!! + window.minOrNull()!!
}

println(solve1(nums))
println(solve2(nums))