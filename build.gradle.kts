import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("jvm") version "1.5.10"
    `maven-publish`
}

group = "lazy"
version = "0.0.1"

repositories {
    mavenCentral()
}

/*dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}*/

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    val propertiesFile = file("local.properties")
    val properties = Properties()
    properties.load(propertiesFile.inputStream())

    repositories {
        maven {
            name = "lazyGithubPackages"
            url = uri("https://maven.pkg.github.com/lazyMods/k-config")
            credentials {
                username = properties.getProperty("gpr.user") as String
                password = properties.getProperty("gpr.key") as String
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}