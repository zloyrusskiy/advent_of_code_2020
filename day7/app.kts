class Graph {
    private data class Vertex(val name: String) {
        val parents = mutableSetOf<Vertex>()
        val children = mutableListOf<Pair<Int, Vertex>>()
    }

    private val vertices = mutableMapOf<String, Vertex>()

    private fun addChild(parent: Vertex, child: Pair<Int, Vertex>) {
        parent.children.add(child)
        child.component2().parents.add(parent)
    }

    fun addVertex(name: String) {
        if (!vertices.containsKey(name)) {
            vertices[name] = Vertex(name)
        }
    }

    fun addChild(parent: String, child: String, childQty: Int) =
        addChild(vertices[parent]!!, Pair(childQty, vertices[child]!!))

    fun parents(name: String): List<String> =
        vertices[name]?.parents?.map { it.name } ?: listOf()

    fun children(name: String): List<Pair<Int, String>> =
        vertices[name]?.children?.map { Pair(it.component1(), it.component2().name) } ?: listOf()

    fun getVertices() = vertices.keys.toList()
}

val lines = generateSequence(::readLine).toList()
val bags_graph = linesToGraph(lines)

fun linesToGraph(lines: List<String>): Graph {
    val graph = Graph()
    lines.forEach { line ->
        val top_regex = """^(\w+ \w+) bags contain (.+)$""".toRegex(RegexOption.MULTILINE)
        val (bag_name, contains_part) = top_regex.find(line)!!.destructured
//        println(Pair(bag_name, contains_part))
        graph.addVertex(bag_name)
        contains_part.split(',').forEach { child_line ->
            val (qty, child_bag_name) = """\s*(\d*)\s?(\w+ \w+) bags?""".toRegex().find(child_line)!!.destructured
//            println(Pair(qty, child_bag_name))
            if (child_bag_name != "no other") {
                graph.addVertex(child_bag_name)
                graph.addChild(bag_name, child_bag_name, qty.toInt())
            }

        }
    }

    return graph
}

fun solve1(bags: Graph): Int {
    val toVisit = bags.parents("shiny gold").toMutableList()
    var shiny_gold_parents = mutableSetOf<String>()

    while (toVisit.isNotEmpty()) {
        val current_bag = toVisit.removeFirst()
        val current_parents = bags.parents(current_bag)

//        println(Pair(current_bag, current_parents))

        shiny_gold_parents.add(current_bag)

        toVisit += current_parents
    }

    return shiny_gold_parents.size
}

fun solve2(bags: Graph, cur_bag_name: String, multiplier: Int): Int {
    val children = bags.children(cur_bag_name)
    println(Pair(cur_bag_name, children))
    if (children.isEmpty()) {
        return 0
    } else {
        return multiplier * children.sumBy { it.component1() + solve2(bags, it.component2(), it.component1()) }
    }
}

//println(lines)
//println(solve1(bags_graph))
println(solve2(bags_graph, "shiny gold", 1))