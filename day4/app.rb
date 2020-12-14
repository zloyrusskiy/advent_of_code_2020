input = ARGF.read
passports = input.split(/^\s*$/)
                 .map do |lines|
  lines.lines.each_with_object({}) do |line, res|
    line.strip.split(' ').each { |i| k, v = i.split(':'); res[k] = v; }
    res
  end
end

MANDATORY_FIELDS1 = %w[byr iyr eyr hgt hcl ecl pid].freeze

def solve1(passports)
  passports.count do |pass|
    MANDATORY_FIELDS1.all? { |k| pass.key?(k) }
  end
end

def proc_from(&block)
  Proc.new &block
end

EYE_COLORS = %w[amb blu brn gry grn hzl oth].freeze
RULES = {
  'byr' => ->(v) { !(v =~ /^\d{4}$/).nil? && (1920..2002).include?(v.to_i) },
  'iyr' => ->(v) { !(v =~ /^\d{4}$/).nil? && (2010..2020).include?(v.to_i) },
  'eyr' => ->(v) { !(v =~ /^\d{4}$/).nil? && (2020..2030).include?(v.to_i) },
  'hgt' => proc_from do |v|
    num = v[0..-3].to_i
    uom = v[-2..-1]
    (uom == 'in' && (59..76).include?(num)) || (uom == 'cm' && (150..193).include?(num))
  end,
  'hcl' => ->(v) { !(v =~ /^\#[0-9a-f]{6}$/).nil? },
  'ecl' => ->(v) { EYE_COLORS.include? v },
  'pid' => ->(v) { !(v =~ /^\d{9}$/).nil? }
}.freeze

def solve2(passports)
  passports.count do |pass|
    RULES.all? { |k, rule| pass.key?(k) && rule.call(pass[k].to_s) }
  end
end

# pp passports
# pp solve1(passports)
pp solve2(passports)