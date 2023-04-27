plugins {
    kotlin("jvm") version "1.8.20"
    `maven-publish`
}

group = "de.hypercdn.extensions"
version = project.findProperty("revision") ?: "UNKNOWN"

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

publishing {
    publications {
        create<MavenPublication>("github") {
            group = project.group.toString()
            artifactId  = project.name.toString()
            version = project.version.toString()

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/HyperCDN/okhttp-ktx")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
