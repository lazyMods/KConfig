package lazy.kconfig

import java.nio.file.Files
import java.nio.file.Path

object KConfig {

    lateinit var id: String
    private var loadedConfigs = listOf<ConfigEntry>()

    fun init(filePath: Path) {
        id = filePath.fileName.toString()
        if (!Files.exists(filePath)) {
            println("[KConfig] Creating config file with id: $id")
            Files.createFile(filePath)
        }
        loadedConfigs = ConfigParser.parse(filePath)
    }

    fun createIntRangeConfigHolder(key: String, defaultValue: Int, range: Pair<Int, Int>): ConfigHolder<Int> {
        val holder = internalCreateConfigHolder(key, defaultValue, "Ranged Value: Greater than ${range.first} and less than ${range.second}")
        if (isInRangeInt(range, holder.get())) return holder
        println("[KConfig] Loaded value isn't in range! [${holder.get()}] Creating holder with default value. [$defaultValue]")
        return ConfigHolder(defaultValue)
    }

    fun createDoubleRangeConfigHolder(key: String, defaultValue: Double, range: Pair<Double, Double>): ConfigHolder<Double> {
        val holder = internalCreateConfigHolder(key, defaultValue, "Ranged Value: Greater than ${range.first} and less than ${range.second}")
        if (isInRangeDouble(range, holder.get())) return holder
        println("[KConfig] Loaded value isn't in range! [${holder.get()}] Creating holder with default value. [$defaultValue]")
        return if (isInRangeDouble(range, holder.get())) return holder else ConfigHolder(defaultValue)
    }

    fun <T> createConfigHolder(key: String, defaultValue: T): ConfigHolder<T> {
        return internalCreateConfigHolder(key, defaultValue, "")
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> internalCreateConfigHolder(key: String, defaultValue: T, comment: String): ConfigHolder<T> {
        if (getByKey(key) == null) {
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: $id")
            ConfigParser.addConfigEntry(ConfigEntry(key, defaultValue), comment)
            return ConfigHolder(defaultValue)
        }
        val fromKey = ConfigHolder(getByKey(key)!!.value as T)
        if (defaultValue != fromKey.get())
            println("[KConfig] Loaded $key from file with value: ${fromKey.get()} [Default is $defaultValue]")
        return fromKey
    }

    private fun getByKey(key: String): ConfigEntry? {
        loadedConfigs.runCatching {
            return first { it.key == key }
        }.onFailure {
            println("[KConfig] Couldn't find key: --> $key <--. Config File: $id")
        }
        return null
    }

    private fun isInRangeInt(range: Pair<Int, Int>, value: Int): Boolean = containsInt(value, range)
    private fun isInRangeDouble(range: Pair<Double, Double>, value: Double): Boolean = containsDouble(value, range)
    private fun containsInt(value: Int, range: Pair<Int, Int>): Boolean = range.first <= value && value <= range.second
    private fun containsDouble(value: Double, range: Pair<Double, Double>): Boolean = range.first <= value && value <= range.second
}