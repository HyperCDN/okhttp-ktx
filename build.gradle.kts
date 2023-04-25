plugins {
    kotlin("jvm") version "1.8.0"
}

group = "de.hypercdn.extensions"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven( url = uri("https://jitpack.io"))
}

dependencies {
    compileOnly("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    compileOnly("ch.qos.logback:logback-classic:1.4.7")
    compileOnly("com.github.HyperCDN:sockslib:ce6723d340")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}