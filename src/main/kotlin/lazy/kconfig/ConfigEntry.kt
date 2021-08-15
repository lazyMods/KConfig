package lazy.kconfig

internal class ConfigEntry(
    val key: String,
    val value: Any?
) {
    override fun toString(): String {
        return "lazy.kconfig.ConfigEntry(key='$key', value=$value)"
    }
}