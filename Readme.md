# KConfig 💾 [WIP]

Configuration library that doesn't depend on a mod to work.
The syntax used is like a baby version of TOML.

### TODO: 

**TOML Parse:**
* [x] Parse values like: **String, Double, Boolean and Integer**
* [ ] Parse array of values
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
* [ ] Min and Max allowed.

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

### Version: 0.0.2

```kotlin
KConfig.init(Paths.get("test.toml"))
//ConfigHolder holds the value from the key specified with default
var name: ConfigHolder<String> = KConfig.createConfigHolder("name", "Marco")
//Using ConfigHolder::get you get the held value
println(name.get())
```

##### Configuration File:
```toml
[pineappleOnPizza]
value=false
[name]
value="Marco"
[height]
value=1.69
[age]
value=23
```
