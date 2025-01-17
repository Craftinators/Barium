plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "me.craftinators"
version = "0.0.0"
description = "Does something"

val minecraftServerVersion = "1.21" // Minecraft Server Version
val protocolLibVersion = "5.3.0" // ProtocolLib Plugin Version

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    } // papermc-repo
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    } // sonatype
    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    } // ProtocolLib
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${minecraftServerVersion}-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:${protocolLibVersion}")
}

val externalPlugins = runPaper.downloadPluginsSpec {
    url("https://github.com/dmulloy2/ProtocolLib/releases/download/${protocolLibVersion}/ProtocolLib.jar") // ProtocolLib
}

tasks.runServer {
    downloadPlugins.from(externalPlugins)
    minecraftVersion(minecraftServerVersion)
}
