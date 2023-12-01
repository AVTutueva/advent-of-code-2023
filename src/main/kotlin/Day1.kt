fun calibrationValuesDigitsOnlySum(lines: List<String>): Int {
    var sum = 0
    for (line in lines){
        val digits = line.removeNonDigits()
        sum += "${digits[0]}${digits[digits.length - 1]}".toInt()
    }
    return sum
}

fun calibrationValuesDigitsLettersSum(lines: List<String>): Int {
    val map = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
        "1" to "1",
        "2" to "2",
        "3" to "3",
        "4" to "4",
        "5" to "5",
        "6" to "6",
        "7" to "7",
        "8" to "8",
        "9" to "9"
    )

    var sum = 0
    for (line in lines){
        val pairs = findKeyIndexPairs(line, map)
        val sortedPairs = pairs.sortedBy { it.second }

        sum += "${map[sortedPairs[0].first]}${map[sortedPairs[sortedPairs.size - 1].first]}".toInt()
    }
    return sum
}



fun findKeyIndexPairs(input: String, map: Map<String, String>): List<Pair<String, Int>> {
    val pairs = mutableListOf<Pair<String, Int>>()

    for ((key, value) in map) {
        var index = input.indexOf(key)
        while (index != -1) {
            pairs.add(key to index)
            index = input.indexOf(key, index + 1)
        }
    }

    return pairs.sortedBy { it.second }
}

