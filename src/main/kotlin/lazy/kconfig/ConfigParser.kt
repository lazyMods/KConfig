package lazy.kconfig

import java.nio.charset.Charset
import java.nio.file.Path

internal object ConfigParser {

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
                                if (hasMinMax) parseValue(type, getMinAndMax(params[3]).first) else null,
                                if (hasMinMax) parseValue(type, getMinAndMax(params[3]).second) else null
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