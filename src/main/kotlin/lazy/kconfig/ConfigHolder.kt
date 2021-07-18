package lazy.kconfig

class ConfigHolder<T>(private val value: T?){
    fun get(): T = value!!
}