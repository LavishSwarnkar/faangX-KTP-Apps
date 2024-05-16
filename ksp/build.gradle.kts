plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.faangx.ktp"
            artifactId = "ksp"
            version = "1.0"

            from(components["java"])
        }
    }
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

    implementation("com.facebook:ktfmt:0.49")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
//kotlin {
//    jvmToolchain(11)
//}