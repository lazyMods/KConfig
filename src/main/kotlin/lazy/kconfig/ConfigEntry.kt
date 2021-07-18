package lazy.kconfig

class ConfigEntry(
    val key: String,
    val value: Any,
    val min: Any? = null,
    val max: Any? = null
) {
    fun getAsDouble(): Double {
        return value as Double
    }

    override fun toString(): String {
        return "lazy.kconfig.ConfigEntry(key='$key', value=$value, min=$min, max=$max)"
    }
}