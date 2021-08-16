package lazy.kconfig

import java.nio.file.Files
import java.nio.file.Path

object KConfig {

    lateinit var id: String
    private var loadedConfigs = listOf<ConfigEntry>()
    private var cache = mutableMapOf<String, Any?>()

    fun createBuilder(filePath: Path): KBuilder {
        id = filePath.fileName.toString()
        if (!Files.exists(filePath)) {
            println("[KConfig] Creating config file with id: $id")
            Files.createFile(filePath)
        }
        loadedConfigs = ConfigParser.parse(filePath)
        loadedConfigs.forEach { cache[it.key] = it.value }
        return KBuilder(cache)
    }
}