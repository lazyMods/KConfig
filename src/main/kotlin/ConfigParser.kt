import java.lang.IllegalArgumentException
import java.nio.charset.Charset
import java.nio.file.Path

object ConfigParser {

    /**
     * Loads the given file, assuming that is a "valid" config file, and reads it line
     * by line, if the line doesn't have the requirements is ignored
     *
     * A valid config is:
     * config_key=value=min>max
     *
     * type = the type
     * configKey = uniqueName
     * value = the value that the configKey holds
     * minMax = the max and the min value
     *
     * @param filePath path to the file to be loaded
     * @return a list of ConfigEntry
     */
    fun load(filePath: Path): List<ConfigEntry> {
        val configs = mutableListOf<ConfigEntry>()
        filePath.toFile().readLines(Charset.defaultCharset()).forEach {
            if (it.startsWith('#').not()) {
                if (it.contains('=')) {
                    val params = it.split('=')
                    val type = params[0]
                    val configKey = params[1]
                    val value = params[2]
                    val hasMinMax = params.size == 4

                    parseValue(type, value)?.run {
                        configs.add(
                            ConfigEntry(
                                configKey,
                                this,
                                if(hasMinMax) parseValue(type, getMinAndMax(params[3]).first) else null,
                                if(hasMinMax) parseValue(type, getMinAndMax(params[3]).second) else null
                            )
                        )
                    } ?: run {
                        println("Couldn't parse type: $type of $configKey")
                    }
                }
            }
        }
        return configs
    }

    private fun getMinAndMax(param: String): Pair<String, String> {
        val minAndMax = param.split('>')
        return Pair(minAndMax[0], minAndMax[1])
    }

    private fun parseValue(type: String, toParse: String): Any? {
        when (type) {
            "string" -> return toParse
            "boolean" -> return toParse.toBoolean()
            "int" -> return toParse.toInt()
            "double" -> return toParse.toDouble()
            "float" -> return toParse.toFloat()
        }
        return null
    }
}