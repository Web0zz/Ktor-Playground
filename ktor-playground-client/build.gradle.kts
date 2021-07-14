plugins {
    kotlin("jvm") version "1.5.21"
}

group = "com.web0zz"
version = "0.0.1"

repositories {
    mavenCentral()
}

val ktor_version: String by project

dependencies {
    implementation("io.ktor:ktor-client-websockets:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
}