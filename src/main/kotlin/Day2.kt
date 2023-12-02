private val colorMap = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)
fun getGamesSum(lines: List<String>): Int {
    var sum = 0
    for (line in lines){
       sum += getGamePoint(line)
    }
    return sum
}

fun getGamesPowerSum(lines: List<String>): Int {
    var sum = 0
    for (line in lines){
        sum += getGamePower(line)
    }
    return sum
}


fun getGamePoint(game: String): Int{
    val splitGameResult = game.split(Regex(":"))
    val gameId = splitGameResult[0].replace(Regex("Game "), "").toInt()

    val rounds = splitGameResult[1].trim().split(Regex(";"))

    for (round in rounds){
        val sets = round.trim().split(',')
        for (set in sets){
            val splitSet = set.trim().split(" ")
            if (!isGameValid(splitSet[1].trim(), splitSet[0].trim().toInt())) {
                return 0
            }
        }
    }
    return gameId
}

fun isGameValid(color: String, number: Int): Boolean {
    return number <= colorMap[color]!!
}

fun getGamePower(game: String): Int{
    val splitGameResult = game.split(Regex(":"))

    val rounds = splitGameResult[1].trim().split(Regex(";"))

    var maxCubMap = mutableMapOf(
        "red" to 0,
        "green" to 0,
        "blue" to 0
    )

    for (round in rounds){
        val sets = round.trim().split(',')
        for (set in sets){
            val splitSet = set.trim().split(" ")
            val color = splitSet[1].trim()
            val number = splitSet[0].trim().toInt()

            if(maxCubMap[color]!! < number){
                maxCubMap[color] = number
            }
        }
    }

    return maxCubMap.values.reduce { acc, num -> acc * num }
}
