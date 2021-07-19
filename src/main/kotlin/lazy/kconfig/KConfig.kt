package lazy.kconfig

import java.nio.file.Path

object KConfig {

    lateinit var id: String
    private var loadedConfigs = listOf<ConfigEntry>()

    fun init(filePath: Path) {
        id = filePath.fileName.toString()
        loadedConfigs = ConfigParser.parse(filePath)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> createConfigHolder(key: String): ConfigHolder<T> = ConfigHolder(getByKey(key)!!.value as T)

    private fun getByKey(key: String): ConfigEntry? {
        loadedConfigs.runCatching {
            return first { it.key == key }
        }.onFailure {
            println("[KConfig] Couldn't find key: --> $key <--. Config File: $id")
        }
        return null
    }
}