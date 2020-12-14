input = ARGF.read
input_data = input.lines.map { |l| l.strip.split(/:? /) }

def solve1(inp)
  res = inp.map do |qty, char, str|
    qty_range = Range.new(*qty.split('-').map(&:to_i))
    char_qty = str.chars.count { |ch| ch == char }
    qty_range.include?(char_qty)
  end

  res.select { |r| r}.count
end

def solve2(inp)
  res = inp.map do |qty, char, str|
    pos_a, pos_b = qty.split('-').map(&:to_i)
    (str[pos_a - 1] == char) ^ (str[pos_b - 1] == char)
  end

  res.select { |r| r}.count
end

# pp solve1(input_data)
pp solve2(input_data)