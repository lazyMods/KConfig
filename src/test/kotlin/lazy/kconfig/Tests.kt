package lazy.kconfig

import org.junit.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Tests {

    private val load = ConfigParser.parse(Paths.get("test.toml"))

    @Test
    fun containsConfig() {
        assertEquals(true, load.any { it.key == "pineappleOnPizza" })
        assertEquals(true, load.any { it.key == "name" })
        assertEquals(true, load.any { it.key == "height" })
        assertEquals(true, load.any { it.key == "age" })
    }

    @Test
    fun configHolder() {
        KConfig.init(Paths.get("test.toml"))
        val age = KConfig.createConfigHolder<Int>("age")
        val name = KConfig.createConfigHolder<String>("name")
        val pineappleOnPizza = KConfig.createConfigHolder<Boolean>("pineappleOnPizza")
        val height = KConfig.createConfigHolder<Double>("height")
        assertEquals(23, age.get())
        assertEquals("Marco", name.get())
        assertEquals(false, pineappleOnPizza.get())
        assertEquals(1.69, height.get())
    }

    @Test
    fun booleanTest() = assertEquals(false, load.first { it.key == "pineappleOnPizza" }.value)

    @Test
    fun stringTest() = assertEquals("Marco", load.first { it.key == "name" }.value)

    @Test
    fun intTest() = assertEquals(23, load.first { it.key == "age" }.value)

    @Test
    fun doubleTest() = assertEquals(1.69, load.first { it.key == "height" }.value)
}