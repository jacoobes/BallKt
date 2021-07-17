
plugins {
    kotlin("multiplatform") version "1.5.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
}

group = "dev.seren"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }

    }

    js(LEGACY) {
        nodejs {
            useCommonJs()
        }
     binaries.executable()
    }
    val serializationVersion = "1.2.0"
    val ktorVersion = "1.6.1"
    val coroutineVers = "1.5.1"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVers")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVers")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(npm("node-fetch", "2.6.1"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
            }
        }
    }
}
