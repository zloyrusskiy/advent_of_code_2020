val lines = generateSequence(::readLine).toList()
val instructions = linesToInstructions(lines)

data class Instruction(val operation: String, val argument: Int)

fun linesToInstructions(lines: List<String>): List<Instruction> {
  return lines.map {
    val (op, arg) = """^(\w+) ([\-\+]\d+)$""".toRegex().find(it)!!.destructured
    Instruction(op, arg.toInt())
  }
}

fun solver(instructions: List<Instruction>): Pair<Int, Int> {
  var accumulator = 0
  var cur_ind = 0
  val visited = mutableSetOf<Int>()

  loop@ while (!visited.contains(cur_ind) && cur_ind < instructions.size) {
    visited.add(cur_ind)
    when (instructions[cur_ind].operation) {
      "acc" -> accumulator += instructions[cur_ind].argument
      "jmp" -> {
        cur_ind += instructions[cur_ind].argument
        continue@loop
      }
    }

    cur_ind += 1
  }

  return Pair(accumulator, cur_ind)
}

fun solve1(instructions: List<Instruction>): Int {
  return solver(instructions).component1()
}

fun solve2(instructions: List<Instruction>): Int {
  instructions.forEachIndexed {
    index, inst ->
      var need_to_solve = false
      val modified_instructions = instructions.map { it.copy() }.toMutableList()
      when (inst.operation) {
        "jmp" -> {
          need_to_solve = true
          modified_instructions[index] = Instruction("nop", modified_instructions[index].argument)
        }
        "nop" -> {
          need_to_solve = true
          modified_instructions[index] = Instruction("jmp", modified_instructions[index].argument)
        }
      }
//      println(modified_instructions)

      if (need_to_solve) {
        val (acc, ret_ind) = solver(modified_instructions)

//        println(Pair(acc,ret_ind))

        if (ret_ind == instructions.size) {
          return acc
        }
      }

  }

  return -100500
}

//println(instructions)
//println(solve1(instructions))
println(solve2(instructions))