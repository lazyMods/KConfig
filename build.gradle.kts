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

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    val propertiesFile = file("local.properties")
    val properties = Properties()
    properties.load(propertiesFile.inputStream())

    repositories {
        maven {
            name = "lazy"
            url = uri("https://lazy-maven-repo.herokuapp.com/releases")
            credentials {
                username = properties.getProperty("user") as String
                password = properties.getProperty("key") as String
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}