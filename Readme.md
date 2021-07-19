# KConfig [WIP]

Configuration library that doesn't depend on a mod to be installed.

### How to use:

Currently, you need to clone this repo and build the project to get the jar file.

### 0.0.1 Version

* **type** - config data type
    * **string**, boolean, double, int
* **configKey** - unique name to define the config
* **value** - the value that **configKey** holds
* **minMax** - the min and the max value separated with a **>** character

| Type   | Config Entry | Value     | Min/Max |
|--------|--------------|-----------|---------|
| string | playerName   | kina      |         |
| int    | age          | 18        | 18/100  |
| float  | size         | 1.5       | 1.4/2   |
| double | something    | 0.0000001 | 0/1     |

### Example
```
string=playerName=kina
int=age=18=18>100
```
