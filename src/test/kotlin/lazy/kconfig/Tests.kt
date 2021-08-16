package lazy.kconfig

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.nio.file.Paths
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class Tests {

    @Test
    @Order(0)
    fun configHolder() {
        initKC()
        val age = KConfig.createConfigHolder("age", 23)
        val name = KConfig.createConfigHolder("name", "Marco")
        val pineappleOnPizza = KConfig.createConfigHolder("pineappleOnPizza", false)
        val height = KConfig.createConfigHolder("height", 1.69)
        val msgTest = KConfig.createConfigHolder("msgTest", 10)
        val intArray = KConfig.createConfigHolder("intArray", arrayListOf(10, 11, 11, 12, 12, 14))
        val booleanArray = KConfig.createConfigHolder("booleanArray", arrayListOf(false, false, false))
        val doubleArray = KConfig.createConfigHolder("doubleArray", arrayListOf(0.2, 4.0, 3.2))
        val animes = KConfig.createConfigHolder("animes", arrayListOf("Naruto", "Dragon Ball", "HunterXHunter"))
        val intRange = KConfig.createIntRangeConfigHolder("intRange", 10, Pair(0, Int.MAX_VALUE))
        val doubleRange = KConfig.createDoubleRangeConfigHolder("doubleRange", 1.0, Pair(0.0, Double.MAX_VALUE))
        assertEquals(23, age.get())
        assertEquals("Marco", name.get())
        assertEquals(arrayListOf("Naruto", "Dragon Ball", "HunterXHunter"), animes.get())
        assertEquals(false, pineappleOnPizza.get())
        assertEquals(1.69, height.get())
        assertEquals(10, msgTest.get())
        assertEquals(arrayListOf(10, 11, 11, 12, 12, 14), intArray.get())
        assertEquals(arrayListOf(false, false, false), booleanArray.get())
        assertEquals(arrayListOf(0.2, 4.0, 3.2), doubleArray.get())
        assertEquals(10, intRange.get())
        assertEquals(1.0, doubleRange.get())
    }

    @Test
    @Order(1)
    fun loadMsgTest() {
        initKC()
        assertEquals(10, KConfig.createConfigHolder("msgTest", 100).get())
    }

    @Test
    @Order(1)
    fun booleanTest() {
        initKC()
        assertEquals(false, KConfig.createConfigHolder("pineappleOnPizza", false).get())
    }

    @Test
    @Order(1)
    fun stringTest() {
        initKC()
        assertEquals("Marco", KConfig.createConfigHolder("name", "Marco").get())
    }

    @Test
    @Order(1)
    fun intTest() {
        initKC()
        assertEquals(23, KConfig.createConfigHolder("age", 23).get())
    }

    @Test
    @Order(1)
    fun doubleTest() {
        initKC()
        assertEquals(1.69, KConfig.createConfigHolder("height", 1.69).get())
    }

    @Test
    @Order(1)
    fun stringArrayTest() {
        initKC()
        assertEquals(
            arrayListOf("Naruto", "Dragon Ball", "HunterXHunter"),
            KConfig.createConfigHolder("animes", arrayListOf("Naruto", "Dragon Ball", "Death Note", "HunterXHunter")).get()
        )
    }

    @Test
    @Order(1)
    fun arrayContainValue() {
        initKC()
        assertEquals(true, KConfig.createConfigHolder("animes", arrayListOf("Naruto", "Dragon Ball", "Death Note", "HunterXHunter")).get().contains("Naruto"))
    }

    @Test
    @Order(1)
    fun intArrayTest() {
        initKC()
        assertEquals(arrayListOf(10, 11, 11, 12, 12, 14), KConfig.createConfigHolder("intArray", arrayListOf(10, 1, 11, 11, 12, 12, 14)).get())
    }

    @Test
    @Order(1)
    fun booleanArrayTest() {
        initKC()
        assertEquals(arrayListOf(false, false, false), KConfig.createConfigHolder("booleanArray", arrayListOf(false, false, false, true)).get())
    }

    @Test
    @Order(1)
    fun doubleArrayTest() {
        initKC()
        assertEquals(arrayListOf(0.2, 4.0, 3.2), KConfig.createConfigHolder("doubleArray", arrayListOf(0.2, 3.2)).get())
    }

    @Test
    @Order(1)
    fun intRangeTest() {
        initKC()
        assertEquals(10, KConfig.createIntRangeConfigHolder("intRange", 10, Pair(0, Int.MAX_VALUE)).get())
    }

    @Test
    @Order(1)
    fun doubleRangeTest() {
        initKC()
        assertEquals(1.0, KConfig.createDoubleRangeConfigHolder("doubleRange", 1.0, Pair(0.0, Double.MAX_VALUE)).get())
    }

    private fun initKC() {
        KConfig.init(Paths.get("test.toml"))
    }
}