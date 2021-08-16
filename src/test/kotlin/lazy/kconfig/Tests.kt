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
        val builder = initKC()
        val age = builder.defineInt("age", 23)
        val name = builder.defineString("name", "Marco")
        val pineappleOnPizza = builder.defineBoolean("pineappleOnPizza", false)
        val height = builder.defineDouble("height", 1.69)
        val msgTest = builder.defineInt("msgTest", 10)
        val intArray = builder.defineIntArray("intArray", arrayListOf(10, 11, 11, 12, 12, 14))
        val booleanArray = builder.defineBooleanArray("booleanArray", arrayListOf(false, false, false))
        val doubleArray = builder.defineDoubleArray("doubleArray", arrayListOf(0.2, 4.0, 3.2))
        val animes = builder.defineStringArray("animes", arrayListOf("Naruto", "Dragon Ball", "HunterXHunter"))
        val intRange = builder.defineIntRange("intRange", 10, 0, Int.MAX_VALUE)
        val doubleRange = builder.defineDoubleRange("doubleRange", 1.0, 0.0, Double.MAX_VALUE)
        builder.build()
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
        val builder = initKC()
        val msgTest = builder.defineInt("msgTest", 100)
        builder.build()
        assertEquals(10, msgTest.get())
    }

    @Test
    @Order(1)
    fun booleanTest() {
        val builder = initKC()
        val pineappleOnPizza = builder.defineBoolean("pineappleOnPizza", false)
        builder.build()
        assertEquals(false, pineappleOnPizza.get())
    }

    @Test
    @Order(1)
    fun stringTest() {
        val builder = initKC()
        val name = builder.defineString("name", "Marco")
        builder.build()
        assertEquals("Marco", name.get())
    }

    @Test
    @Order(1)
    fun intTest() {
        val builder = initKC()
        val age = builder.defineInt("age", 23)
        builder.build()
        assertEquals(23, age.get())
    }

    @Test
    @Order(1)
    fun doubleTest() {
        val builder = initKC()
        val height = builder.defineDouble("height", 1.69)
        builder.build()
        assertEquals(1.69, height.get())
    }

    @Test
    @Order(1)
    fun stringArrayTest() {
        val builder = initKC()
        val animes = builder.defineStringArray("animes", arrayListOf("Naruto", "Dragon Ball", "Death Note", "HunterXHunter"))
        builder.build()
        assertEquals(arrayListOf("Naruto", "Dragon Ball", "HunterXHunter"), animes.get())
    }

    @Test
    @Order(1)
    fun arrayContainValue() {
        val builder = initKC()
        val animes = builder.defineStringArray("animes", arrayListOf("Naruto", "Dragon Ball", "Death Note", "HunterXHunter"))
        builder.build()
        assertEquals(true, animes.get().contains("Naruto"))
    }

    @Test
    @Order(1)
    fun intArrayTest() {
        val builder = initKC()
        val intArray = builder.defineIntArray("intArray", arrayListOf(10, 1, 11, 11, 12, 12, 14))
        builder.build()
        assertEquals(arrayListOf(10, 11, 11, 12, 12, 14), intArray.get())
    }

    @Test
    @Order(1)
    fun booleanArrayTest() {
        val builder = initKC()
        val booleanArray = builder.defineBooleanArray("booleanArray", arrayListOf(false, false, false, true))
        builder.build()
        assertEquals(arrayListOf(false, false, false), booleanArray.get())
    }

    @Test
    @Order(1)
    fun doubleArrayTest() {
        val builder = initKC()
        val doubleArray = builder.defineDoubleArray("doubleArray", arrayListOf(0.2, 3.2))
        builder.build()
        assertEquals(arrayListOf(0.2, 4.0, 3.2), doubleArray.get())
    }

    @Test
    @Order(1)
    fun intRangeTest() {
        val builder = initKC()
        val intRange = builder.defineIntRange("intRange", 10, 0)
        builder.build()
        assertEquals(10, intRange.get())
    }

    @Test
    @Order(1)
    fun doubleRangeTest() {
        val builder = initKC()
        val doubleRange = builder.defineDoubleRange("doubleRange", 1.0, 0.0)
        builder.build()
        assertEquals(1.0, doubleRange.get())
    }

    @Test
    @Order(1)
    fun builderTest() {
        val builder = KConfig.createBuilder(Paths.get("test.toml"))
        val name = builder.defineStringArray("name", arrayListOf("Marco", "Santos"))
        val age = builder.defineInt("age", 23)
        builder.build()
        assertEquals(arrayListOf("Marco", "Santos"), name.get())
        assertEquals(23, age.get())
    }

    private fun initKC(): KBuilder {
        return KConfig.createBuilder(Paths.get("test.toml"))
    }
}