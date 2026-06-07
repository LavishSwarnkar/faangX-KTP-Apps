import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

group = "com.faangx.ktp"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven("https://jitpack.io")
}

dependencies {
    implementation(compose.desktop.currentOs)

    // MiniApps come from the local :apps module instead of JitPack.
    implementation(project(":apps"))

    implementation("org.jetbrains.compose.material3:material3-desktop:1.6.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.3")
}

tasks.test {
    useJUnitPlatform()
}

compose.desktop {
    application {
        // Real entry point: the top-level main() in MiniAppsCatalog.kt
        mainClass = "com.faangx.ktp.catalog.MiniAppsCatalogKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MiniAppsCatalog"
            packageVersion = "1.0.0"
        }
    }
}
