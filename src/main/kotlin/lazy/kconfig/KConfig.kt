package lazy.kconfig

import java.nio.file.Files
import java.nio.file.Path

object KConfig {

    lateinit var id: String
    private var loadedConfigs = listOf<ConfigEntry>()

    fun init(filePath: Path) {
        id = filePath.fileName.toString()
        if(!Files.exists(filePath)){
            println("[KConfig] Creating config file with id: $id")
            Files.createFile(filePath)
        }
        loadedConfigs = ConfigParser.parse(filePath)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> createConfigHolder(key: String, defaultValue: T): ConfigHolder<T> {
        if(getByKey(key) == null){
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: $id")
            ConfigParser.addConfigEntry(ConfigEntry(key, defaultValue))
            return ConfigHolder(defaultValue)
        }
        val fromKey = ConfigHolder(getByKey(key)!!.value as T)
        if(defaultValue != fromKey.get())
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
}