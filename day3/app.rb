input = ARGF.read
map = input.lines.map(&:strip)

def solve1(map)
  x = 0
  y = 0
  trees_qty = 0
  while y < map.size
    line = map[y]
    trees_qty += 1 if line[x] == '#'
    x = (x + 3) % line.size
    y += 1
  end

  trees_qty
end

def solve2(map, slope_x, slope_y)
  x = 0
  y = 0
  trees_qty = 0
  while y < map.size
    line = map[y]
    trees_qty += 1 if line[x] == '#'
    x = (x + slope_x) % line.size
    y += slope_y
  end

  trees_qty
end


# puts solve1(map)
puts [[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]].map { |a,b| solve2(map, a, b) }.reduce(:*)