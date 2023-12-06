fun findNumberCoordinates(line: String): MutableList<Pair<Int, Int>> {
    val numbers = mutableListOf<Pair<Int, Int>>()
    val regex = Regex("\\d+")

    val matches = regex.findAll(line)
    for (match in matches) {
        numbers.add(Pair(match.range.first, match.range.last))
    }
    return numbers
}

fun isNumberSurrounded(twoList: List<List<Char>>, numberCoordinates: Pair<Int, Pair<Int, Int>>): Boolean {
    val row = numberCoordinates.first
    val colRange = numberCoordinates.second.first..numberCoordinates.second.second

    for (i in colRange){ // column
        if (row > 0 && !twoList[row - 1][i].isDigit() && twoList[row - 1][i] != '.') return true
        if (i > 0 && !twoList[row][i - 1].isDigit() && twoList[row][i - 1] != '.') return true
        if (i < twoList[row].size - 1 && !twoList[row][i + 1].isDigit() && twoList[row][i + 1] != '.') return true
        if (row < twoList.size - 1 && !twoList[row + 1][i].isDigit() && twoList[row + 1][i] != '.') return true
        if (row > 0 && i > 0 && !twoList[row - 1][i - 1].isDigit() && twoList[row - 1][i - 1] != '.') return true
        if (row > 0 && i < twoList[row].size - 1 && !twoList[row - 1][i + 1].isDigit() && twoList[row - 1][i + 1] != '.') return true
        if (row < twoList.size - 1 && i > 0 && !twoList[row + 1][i - 1].isDigit() && twoList[row + 1][i - 1] != '.') return true
        if (row < twoList.size - 1 && i < twoList[row].size - 1 && !twoList[row + 1][i + 1].isDigit() && twoList[row + 1][i + 1] != '.') return true
    }
    return false
}

fun sumAllNotSurroundedNumbers(lines: List<String>): Int{
    val numbersInfo = findAllNumbers(lines)

    val twoDList = lines.map { it.toList() }

    var sum = 0
    for (number in numbersInfo){
        if (isNumberSurrounded(twoDList, number)){
            val start = number.second.first
            val end = number.second.second + 1
            sum += twoDList[number.first].subList(start, end).joinToString("").toInt()
        }
    }
    return sum
}

fun findAllNumbers(lines: List<String>): MutableList<Pair<Int, Pair<Int, Int>>>{
    val numbersInfo = mutableListOf<Pair<Int, Pair<Int, Int>>>()

    for ((index, line) in lines.withIndex()) {
        val numbers = findNumberCoordinates(line)
        for (number in numbers){
            numbersInfo.add(Pair(index, Pair(number.first, number.second)))
        }
    }

    return numbersInfo
}

data class StarWithNeighbors(
    val coordinates: Pair<Int, Int>,
    val neighbors: MutableList<Pair<Int, Pair<Int, Int>>>
)

fun findStarWithNeighbors(grid: List<List<Char>>): List<Pair<Int, Int>> {
    val starsWithNeighbors = mutableListOf<Pair<Int, Int>>()

    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == '*') {
                val star = Pair(i, j)
                starsWithNeighbors.add(star)
            }
        }
    }

    return starsWithNeighbors
}

fun calculateGear(lines: List<String>): Int {
    val grid = lines.map { it.toList() }
    val stars = findStarWithNeighbors(grid)
    val numbers = findAllNumbers(lines)

    var totalGear = 0

    for (star in stars){
        val starNeighbors = getNeighbors(star)

        var matchingNumbers = mutableListOf<Pair<Int, Pair<Int, Int>>>()

        for (number in numbers){
            val numberNeighbors = flattenPair(number)
            if (starNeighbors.any { it in numberNeighbors }) {
                matchingNumbers.add(number)
            }
        }
        if (matchingNumbers.size == 2){
            val gearValues = matchingNumbers.map { (row, col) ->
                val substring = grid[row].subList(col.first, col.second + 1)
                val gearValue = substring.joinToString("").toInt()
                gearValue
            }

            val gearProduct = gearValues.reduce { acc, value -> acc * value }
            totalGear += gearProduct
        }
    }
    return totalGear
}

fun getNeighbors(coordinates: Pair<Int, Int>): List<Pair<Int, Int>> {
    val row = coordinates.first
    val col = coordinates.second

    val relativeCoordinates = listOf(
        -1 to 0,
        1 to 0,
        0 to -1,
        0 to 1,
        -1 to -1,
        -1 to 1,
        1 to -1,
        1 to 1
    )

    return relativeCoordinates.map { (dx, dy) -> row + dx to col + dy }
}


fun flattenPair(pair: Pair<Int, Pair<Int, Int>>): List<Pair<Int, Int>> {
    val x = pair.first
    val (y0, yn) = pair.second
    return (y0..yn).map { x to it }
}