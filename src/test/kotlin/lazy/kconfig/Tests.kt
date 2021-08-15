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
    fun loadMsgTest() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals(10, KConfig.createConfigHolder("msgTest", 100).get())
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

    @Test
    fun stringArrayTest() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals(
            arrayListOf("\"Naruto\"", "\"Dragon Ball\"", "\"HunterXHunter\""),
            KConfig.createConfigHolder("animes", arrayListOf("\"Naruto\"", "\"Dragon Ball\"", "\"Death Note\"", "\"HunterXHunter\"")).get()
        )
    }

    @Test
    fun arrayContainValue() {
        KConfig.init(Paths.get("test.toml"))
        assertEquals(true, KConfig.createConfigHolder("animes", arrayListOf("\"Naruto\"", "\"Dragon Ball\"", "\"Death Note\"", "\"HunterXHunter\"")).get().contains("\"Naruto\""))
    }

    @Test
    fun intArrayTest() {
        initKC()
        assertEquals(arrayListOf(10, 11, 11, 12, 12, 14), KConfig.createConfigHolder("intArray", arrayListOf(10, 1, 11, 11, 12, 12, 14)).get())
    }

    @Test
    fun booleanArrayTest() {
        initKC()
        assertEquals(arrayListOf(false, false, false), KConfig.createConfigHolder("booleanArray", arrayListOf(false, false, false, true)).get())
    }

    @Test
    fun doubleArrayTest() {
        initKC()
        assertEquals(arrayListOf(0.2, 4.0, 3.2), KConfig.createConfigHolder("doubleArray", arrayListOf(0.2, 3.2)).get())
    }

    private fun initKC() {
        KConfig.init(Paths.get("test.toml"))
    }
}