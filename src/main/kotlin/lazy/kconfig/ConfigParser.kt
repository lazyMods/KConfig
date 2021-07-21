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

    fun addConfigEntry(entry: ConfigEntry){
        val configFile = FileWriter(filePath.toFile(), true)
        configFile.append("[${entry.key}]\n")
        configFile.append("value=")
        val value = if(entry.value is String) "\u0022${entry.value}\u0022\n" else "${entry.value}\n"
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
        if (parse == "false" || parse == "true") return parse.toBoolean()
        if (startsAndEndsWith(parse, '"')) return parse.substring(1, parse.length - 1)
        if (parse.filter { it == '.' }.length == 1) return parse.toDouble()
        return parse.toInt()
    }

    private fun startsAndEndsWith(toCheck: String, char: Char): Boolean = toCheck.startsWith(char) && toCheck.endsWith(char)
    private fun startsAndEndsWith(toCheck: String, start: Char, end: Char): Boolean = toCheck.startsWith(start) && toCheck.endsWith(end)
}
