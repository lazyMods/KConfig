# KConfig 💾 [WIP]

Configuration library that doesn't depend on a mod to work.
The syntax used is like a baby version of TOML.

### TODO: 

**TOML Parse:**
* [x] Parse values like: **String, Double, Boolean and Integer**
* [x] Parse array of values
* [ ] Array of arrays: `[ [1, 2], [23, 2] ]`
* [ ] Multiline array definitions 🔽

```toml
[levels]
value=[
    1, 10, 20, 30
]
```
**Others:**
* [x] Create files if they don't exist.
* [x] Default values when not available.
* [x] Min and Max allowed.
* [ ] Add Maps.

### How to use:

```kotlin
repositories {
    maven("https://pkgs.dev.azure.com/lazyio/maven/_packaging/lazy/maven/v1")
}

dependencies {
  val kConfigVersion: String by project
  modImplementation("lazy:kconfig:$kConfigVersion")
  include("lazy:kconfig:$kConfigVersion")
}
```

### Version: 0.0.3

```kotlin
KConfig.init(Paths.get("test.toml"))
//ConfigHolder holds the value from the key specified with default
var name: ConfigHolder<String> = KConfig.createConfigHolder("name", "Marco")
//Using ConfigHolder::get you get the held value
println(name.get())

/* Entries with min and max values.
* Supported types are Int and Doubles.
* If the value isn't in range returns the default value.
* 
* KConfig.createIntRangeConfigHolder
* KConfig.createDoubleRangeConfigHolder
 */
var ranged: ConfigHolder<Int> = KConfig.createIntRangeConfigHolder("intRange", 10, Pair(0, Int.MAX_VALUE))
println(ranged.get())

// Array entry
var blocks: ConfigHolder<List<String>> = KConfig.createConfigHolder("blocks", arrayListOf("diamond_ore", "diamond_block"))
println(blocks.get())
```

##### Configuration File:
```toml
[age]
value=23
[name]
value="Marco"
[pineappleOnPizza]
value=false
[height]
value=1.69
[msgTest]
value=10
[intArray]
value=[10, 11, 11, 12, 12, 14]
[booleanArray]
value=[false, false, false]
[doubleArray]
value=[0.2, 4.0, 3.2]
[animes]
value=["Naruto", "Dragon Ball", "HunterXHunter"]
#Ranged Value: Greater than 0 and less than 2147483647
[intRange]
value=-1
#Ranged Value: Greater than 0.0 and less than 1.7976931348623157E308
[doubleRange]
value=1.0
```
