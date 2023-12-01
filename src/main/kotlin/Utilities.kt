fun readFileAsLinesFromResources(fileName: String): List<String> {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader.getResourceAsStream(fileName)

    return inputStream?.bufferedReader().use { it?.readLines() ?: emptyList() }
}

fun String.removeNonDigits(): String {
    return this.replace(Regex("[^\\d]"), "")
}