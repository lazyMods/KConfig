package lazy.kconfig

import java.nio.file.Path

object KConfig {

    private var loadedConfigs = listOf<ConfigEntry>()

    fun init(filePath: Path) {
        loadedConfigs = ConfigParser.parse(filePath)
    }

    fun <T> createConfigHolder(key: String): ConfigHolder<T> {
        return ConfigHolder(getByKey(key)!!.value as T)
    }

    private fun getByKey(key: String): ConfigEntry? {
        loadedConfigs.runCatching {
            return first { it.key == key }
        }.onFailure {
            println("Couldn't find key: $key")
        }
        return null
    }
}