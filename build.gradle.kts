plugins {
    kotlin("jvm") version "2.0.21"
}

group = "ca.bushtail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.kord.dev/snapshots")
    maven("https://oss.sonatype.org/content/repositories/snapshots")

}

dependencies {
    testImplementation(kotlin("test"))
    implementation("dev.kord:kord-core:0.15.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")
}

tasks.test {
    useJUnitPlatform()
}