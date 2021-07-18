package lazy.kconfig

import org.junit.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Tests {

    private val load = ConfigParser.load(Paths.get("test.props"))

    @Test
    fun containsConfig() {
        assertEquals(true, load.any { it.key == "fooIsBar" })
        assertEquals(true, load.any { it.key == "name" })
    }

    @Test
    fun hasMinMax() {
        assertEquals(1, load.first {it.key == "minMax"}.min)
        assertEquals(10, load.first {it.key == "minMax"}.max)
    }

    @Test
    fun booleanTest() = assertEquals(false, load.first {it.key == "fooIsBar"}.value)

    @Test
    fun stringTest() = assertEquals("Marco", load.first {it.key == "name"}.value)

    @Test
    fun intTest() = assertEquals(10, load.first {it.key == "minMax"}.value)
}