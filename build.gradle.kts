@file:Suppress("PropertyName", "VariableNaming")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.iridium)
    alias(libs.plugins.iridium.publish)
    alias(libs.plugins.iridium.upload)
}

repositories {
    maven("https://teamvoided.org/releases") { content { includeGroup("org.teamvoided") } }
    maven("https://teamvoided.org/snapshots") { content { includeGroup("org.teamvoided") } }
    maven("https://maven.fzzyhmstrs.me/") { name = "FzzyMaven"; content { includeGroup("me.fzzyhmstrs") } }
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
        content {
            includeGroup("com.terraformersmc")
            includeGroup("dev.emi")
        }
    }
    maven("https://api.modrinth.com/maven") { content { includeGroup("maven.modrinth") } }
    mavenCentral()
}

//println("Task: " + gradle.startParameter.taskNames.joinToString(","))

modSettings {
    entrypoint("main", "org.teamvoided.vanillium.Vanillium::init")
    entrypoint("client", "org.teamvoided.vanillium.client.VanilliumClient::init")
    entrypoint("fabric-datagen", "org.teamvoided.vanillium.data.gen.VanilliumData")

    mixinFile("${modId()}.client.mixins.json")
    mixinFile("${modId()}.mixins.json")
    accessWidener("vanillium.classtweaker")
    dependency("fzzy_config", "*")
}

@Suppress("AvoidDuplicateDependencies")
dependencies {
    modImplementation(fileTree("libs"))
    // Dependencies
    modImplementation(libs.fzzy.config)
    modImplementation(libs.voidlib)
    include(libs.voidlib)
    // QoL
    modImplementation(libs.modmenu)
//    modCompileOnly("${libs.emi.get()}:api")
//    modLocalRuntime(libs.emi)
    // Testing
    modImplementation(libs.creative.works)
//    modImplementation(libs.imguimc)
}
val username = "vDev"
val uuid: String? = null

loom {
    splitEnvironmentSourceSets()

    mods {
        register(modSettings.modId()) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }

    runs {
        named("client") {
            programArgs("--username", username)
            ideConfigGenerated(false)
            uuid?.let { programArgs("--uuid", uuid) }
        }

        create("TestWorld") {
            client()
            ideConfigGenerated(false)
            runDir("run")
            programArgs("--quickPlaySingleplayer", "test", "--username", username)
            uuid?.let { programArgs("--uuid", uuid) }
        }

        create("DataGen") {
            client()
            ideConfigGenerated(false)
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.modid=${modSettings.modId()}")
            runDir("build/datagen")
        }
    }
}

sourceSets["main"].resources.srcDir("src/main/generated")

tasks {
    val targetJavaVersion = 21
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile>().all {
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(targetJavaVersion).toString()))
        withSourcesJar()
    }

//    jar {
//        val valTaskNames = gradle.startParameter.taskNames
//        if (!valTaskNames.contains("runDataGen")) {
//            exclude("org/teamvoided/template/data/gen/*")
//        } else {
//            println("Running datagen for task ${valTaskNames.joinToString(" ")}")
//        }
//    }

}

publishScript {
    releaseRepository("TeamVoided", "https://maven.teamvoided.org/releases")
    publication(modSettings.modId(), false)
    publishSources(true)
}

uploadConfig {
//    debugMode = true
    modrinthId = "xRaXieJP"
//    curseId = "0"

    changeLog = File("./changelog.md").readText()
    // FabricApi
    modrinthDependency("P7dR8mSH", uploadConfig.REQUIRED)
//    curseDependency("fabric-api", uploadConfig.REQUIRED)
    // Fabric Language Kotlin
    modrinthDependency("Ha28R6CL", uploadConfig.REQUIRED)
//    curseDependency("fabric-language-kotlin", uploadConfig.REQUIRED)
    // Fzzy config
    modrinthDependency("hYykXjDp", uploadConfig.REQUIRED)
//    curseDependency("fabric-api", uploadConfig.REQUIRED)
}
