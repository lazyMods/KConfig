package lazy.kconfig

@Suppress("UNCHECKED_CAST")
class KBuilder internal constructor(private val cache: Map<String, Any?>) {

    private val entryInfo = mutableListOf<EntryInfo>()

    fun defineBoolean(key: String, defaultValue: Boolean): ConfigHolder<Boolean> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] !is Boolean) defaultValue else cache[key] as Boolean
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineString(key: String, defaultValue: String): ConfigHolder<String> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] !is String) defaultValue else cache[key] as String
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineDouble(key: String, defaultValue: Double): ConfigHolder<Double> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] !is Double) defaultValue else cache[key] as Double
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineInt(key: String, defaultValue: Int): ConfigHolder<Int> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] !is Int) defaultValue else cache[key] as Int
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineIntRange(key: String, defaultValue: Int, min: Int, max: Int = Integer.MAX_VALUE): ConfigHolder<Int> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue, Pair(min, max)))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val configValue: Int = if(isInRangeInt(Pair(min, max), cache[key] as Int)) cache[key] as Int else defaultValue
        if(configValue != defaultValue) println("[KConfig] Loaded $key from file with value: $configValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, configValue, Pair(min, max)))
        return ConfigHolder(configValue)
    }

    fun defineDoubleRange(key: String, defaultValue: Double, min: Double, max: Double = Double.MAX_VALUE): ConfigHolder<Double> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue, Pair(min, max)))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val configValue: Double = if(isInRangeDouble(Pair(min, max), cache[key] as Double)) cache[key] as Double else defaultValue
        if(configValue != defaultValue) println("[KConfig] Loaded $key from file with value: $configValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, configValue, Pair(min, max)))
        return ConfigHolder(configValue)
    }

    fun defineStringArray(key: String, defaultValue: List<String>): ConfigHolder<List<String>> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] is List<*> && (cache[key] as List<*>).getOrNull(0) is String) cache[key] as List<String> else defaultValue
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineBooleanArray(key: String, defaultValue: List<Boolean>): ConfigHolder<List<Boolean>> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] is List<*> && (cache[key] as List<*>).getOrNull(0) is Boolean) cache[key] as List<Boolean> else defaultValue
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineDoubleArray(key: String, defaultValue: List<Double>): ConfigHolder<List<Double>> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] is List<*> && (cache[key] as List<*>).getOrNull(0) is Double) cache[key] as List<Double> else defaultValue
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun defineIntArray(key: String, defaultValue: List<Int>): ConfigHolder<List<Int>> {
        if (cache.isEmpty() || !cache.containsKey(key)) {
            entryInfo.add(EntryInfo(key, defaultValue))
            println("[KConfig] Creating entry with key: --> $key <-- value: $defaultValue. Config File: ${KConfig.id}")
            return ConfigHolder(defaultValue)
        }
        val cacheValue = if(cache[key] is List<*> && (cache[key] as List<*>).getOrNull(0) is Int) cache[key] as List<Int> else defaultValue
        if(cacheValue != defaultValue) println("[KConfig] Loaded $key from file with value: $cacheValue [Default is $defaultValue]")
        entryInfo.add(EntryInfo(key, cacheValue))
        return ConfigHolder(cacheValue)
    }

    fun build() {
        ConfigParser.clearFile()
        entryInfo.forEach {
            val comment = if (it.minMax != null) "Ranged Value: Greater than ${it.minMax.first} and less than ${it.minMax.second}" else ""
            ConfigParser.addConfigEntry(ConfigEntry(it.key, it.defaultValue), comment)
        }
    }

    private fun isInRangeInt(range: Pair<Int, Int>, value: Int): Boolean = containsInt(value, range)
    private fun isInRangeDouble(range: Pair<Double, Double>, value: Double): Boolean = containsDouble(value, range)
    private fun containsInt(value: Int, range: Pair<Int, Int>): Boolean = range.first <= value && value <= range.second
    private fun containsDouble(value: Double, range: Pair<Double, Double>): Boolean = range.first <= value && value <= range.second

    private class EntryInfo(val key: String, val defaultValue: Any?, val minMax: Pair<Any?, Any?>? = null)
}