import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("net.linguica.maven-settings") version "0.5"
    `maven-publish`
}

group = "lazy"
version = "0.0.4"

repositories {
    mavenCentral()
    maven("https://pkgs.dev.azure.com/lazyio/maven/_packaging/lazy/maven/v1") {
        name = "lazy"
        authentication {
            create<BasicAuthentication>("basic")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        maven("https://pkgs.dev.azure.com/lazyio/maven/_packaging/lazy/maven/v1") {
            name = "lazy"
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}