plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

group = "ksp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.20-1.0.14")

    implementation("com.squareup:kotlinpoet:1.9.0")
    implementation("com.squareup:kotlinpoet-metadata:1.9.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
//kotlin {
//    jvmToolchain(11)
//}