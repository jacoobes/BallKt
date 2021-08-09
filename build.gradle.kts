import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "dev.seren"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

val serializationVersion = "1.2.0"
val ktorVersion = "1.6.1"
val coroutineVers = "1.5.1"

dependencies {
    implementation ("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation ("com.github.kittinunf.fuel:fuel-gson:2.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVers")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
