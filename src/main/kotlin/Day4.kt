import kotlin.math.pow

fun calculateScratchcardsPoints(lines: List<String>): Int {
    return lines.sumOf { 2.toDouble().pow(calculateMatchNumbers(it) - 1).toInt() }
}

fun calculateMatchNumbers(line: String): Int {
    val bothStrings = (line.substringAfter(':').split('|'))
    val winningNumbers = bothStrings[0].trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    val numbersIhave = bothStrings[1].trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    val commonNumbers = winningNumbers.intersect(numbersIhave)

    return commonNumbers.size
}

fun calculateScratchcardsPointsWithCopies(lines: List<String>): Int{
    val cardPoints = lines.map { calculateMatchNumbers(it) }.toMutableList()
    val cardPointWithCopies = MutableList(cardPoints.size) { Pair(1, 0) }

    cardPoints.forEachIndexed { index, winCardNumber ->
        if (index + 1 < cardPoints.size) {
            val valueToAdd = cardPointWithCopies[index].first + cardPointWithCopies[index].second

            val start = index + 1
            val end = minOf(index + winCardNumber, cardPoints.size - 1)

            cardPointWithCopies.subList(start, end + 1).forEachIndexed { i, _ ->
                cardPointWithCopies[i + start] = Pair(cardPointWithCopies[i + start].first, cardPointWithCopies[i + start].second + valueToAdd)
            }
        }
    }

    return cardPointWithCopies.sumOf { it.first + it.second }
}
