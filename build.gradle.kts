val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kodein_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.21"
}

group = "com.web0zz"
version = "0.0.1"
application {
    mainClass.set("com.web0zz.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")

    implementation("org.kodein.db:kodein-leveldb-jni-jvm-linux:$kodein_version")
    implementation("org.kodein.db:kodein-db:$kodein_version")
    implementation("org.kodein.db:kodein-db-serializer-kotlinx:$kodein_version")
}
