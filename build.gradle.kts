import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("com.adarshr.test-logger") version "3.2.0"
    application
}

group = "me.kaiqu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.ktor:ktor-client-cio:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.+")
    implementation("org.slf4j:slf4j-simple:1.7.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.junit.platform:junit-platform-runner:1.0.0")
    testImplementation("org.junit.platform:junit-platform-launcher:1.0.0")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.19")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.19")

    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-inline:5.0.0")
    testImplementation("io.ktor:ktor-client-mock:2.3.3")
}

tasks.test {
    useJUnitPlatform() {
        includeEngines("spek2")
    }
}

kotlin {
    jvmToolchain(20)
}

application {
    mainClass.set("MainKt")
}