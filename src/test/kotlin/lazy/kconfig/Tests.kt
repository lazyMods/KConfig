package lazy.kconfig

import org.junit.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Tests {

    @Test
    fun configHolder() {
        KConfig.init(Paths.get("test.toml"))
        val age = KConfig.createConfigHolder("age", 23)
        val name = KConfig.createConfigHolder("name", "Marco")
        val pineappleOnPizza = KConfig.createConfigHolder("pineappleOnPizza", false)
        val height = KConfig.createConfigHolder("height", 1.69)
        assertEquals(23, age.get())
        assertEquals("Marco", name.get())
        assertEquals(false, pineappleOnPizza.get())
        assertEquals(1.69, height.get())
    }

    @Test
    fun booleanTest() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals(false, KConfig.createConfigHolder("pineappleOnPizza", false).get())
    }

    @Test
    fun stringTest() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals("Marco", KConfig.createConfigHolder("name", "Marco").get())
    }

    @Test
    fun intTest() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals(23, KConfig.createConfigHolder("age", 23).get())
    }

    @Test
    fun doubleTest() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals(1.69, KConfig.createConfigHolder("height", 1.69).get())
    }
}