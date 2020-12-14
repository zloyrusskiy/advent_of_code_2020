val content = String(System.`in`.readBytes())
val answers = convertLinesToAnswers(content)

fun convertLinesToAnswers(content: String): List<List<String>> {
    return content.split("\n\n").map { it.split("\n") }
}

fun solve1(answers: List<List<String>>): Int {
    return answers.map { strings -> strings.map { str -> str.toList() }.flatten().distinct().size }.sum()
}

fun solve2(answers: List<List<String>>): Int {
    return answers.map { strings -> strings.map { str -> str.toSet() }.reduce { acc, item -> acc.intersect(item) }}.map { it.size}.sum()
}

//println(answers)
//println(solve1(answers))
println(solve2(answers))