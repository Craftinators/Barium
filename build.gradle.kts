plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "me.craftinators"
version = "0.0.0"
description = "Does something"

val mcVersion = "1.21"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    } // papermc-repo
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    } // sonatype
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
}

tasks {
    runServer {
        minecraftVersion(mcVersion)
    }
}