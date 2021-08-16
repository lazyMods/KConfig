package lazy.kconfig

import java.io.FileWriter
import java.nio.charset.Charset
import java.nio.file.Path

internal object ConfigParser {

    private val entries = mutableMapOf<String, String>()
    private lateinit var filePath: Path

    fun parse(filePath: Path): List<ConfigEntry> {
        this.filePath = filePath;
        val fileLines = filePath.toFile().readLines(Charset.defaultCharset())
        createEntries(fileLines)
        return parseEntries()
    }

    fun addConfigEntry(entry: ConfigEntry, comment: String = "") {
        val configFile = FileWriter(filePath.toFile(), true)
        if (comment.isNotEmpty()) configFile.append("#$comment\n")
        configFile.append("[${entry.key}]\n")
        configFile.append("value=")
        val value = when (entry.value) {
            is String -> {
                "\u0022${entry.value}\u0022\n"
            }
            is List<*> -> {
                val stringBuilder = StringBuilder()
                stringBuilder.append("[")
                entry.value.forEach {
                    if (it is String) {
                        stringBuilder.append("\u0022${it}\u0022, ")
                    } else {
                        stringBuilder.append("$it, ")
                    }
                }
                stringBuilder.substring(0, stringBuilder.length - 2).plus("]\n")
            }
            else -> {
                "${entry.value}\n"
            }
        }
        configFile.append(value)
        configFile.flush()
        configFile.close()
    }

    private fun createEntries(lines: List<String>) {
        lines.forEachIndexed { index, line ->
            if (startsAndEndsWith(line, '[', ']') && lines[index + 1].startsWith("value="))
                entries[line.substring(1, line.length - 1)] = lines[index + 1].substring("value=".length)
        }
    }

    private fun parseEntries(): List<ConfigEntry> {
        val configs = mutableListOf<ConfigEntry>()
        entries.forEach { (key, value) ->
            configs.add(ConfigEntry(key, parseValue(value)))
        }
        return configs
    }

    private fun parseValue(parse: String): Any {
        if (isBoolean(parse)) return parse.toBoolean()
        if (isString(parse)) return parse.substring(1, parse.length - 1)
        if (isDouble(parse)) return parse.toDouble()
        if (isArray(parse)) return parseArray(parse)
        return parse.toInt()
    }

    private fun parseArray(parse: String): Any {
        val tempValues = parse.substring(1, parse.length - 1).split(',')
        val trimValues = arrayListOf<String>()
        tempValues.forEach { trimValues.add(it.trim()) }
        if (trimValues.all { isBoolean(it) }) return trimValues.map { it.toBoolean() }
        if (trimValues.all { isString(it) }) return trimValues.map { it.substring(1, it.length - 1) }
        if (trimValues.all { isDouble(it) }) return trimValues.map { it.toDouble() }
        if (trimValues.all { isInteger(it) }) return trimValues.map { it.toInt() }
        return parse
    }

    private fun isBoolean(parse: String): Boolean = parse == "false" || parse == "true"
    private fun isString(parse: String): Boolean = startsAndEndsWith(parse, '"')
    private fun isDouble(parse: String): Boolean = parse.filter { it == '.' }.length == 1
    private fun isArray(parse: String): Boolean = startsAndEndsWith(parse, '[', ']')
    private fun isInteger(parse: String): Boolean = parse.matches(Regex("[0-9]+"))

    private fun startsAndEndsWith(toCheck: String, char: Char): Boolean = toCheck.startsWith(char) && toCheck.endsWith(char)
    private fun startsAndEndsWith(toCheck: String, start: Char, end: Char): Boolean = toCheck.startsWith(start) && toCheck.endsWith(end)
}
