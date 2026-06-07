// Load the Compose & Kotlin plugins once on the root classpath (apply false) so that all
// subprojects that apply them share a single plugin classloader. Without this, having more
// than one module apply `org.jetbrains.compose` triggers:
//   Shared build service 'ConfigurationProblemReporterService' parameters have unexpected type
plugins {
    kotlin("jvm") apply false
    id("org.jetbrains.compose") apply false
}

repositories {
    mavenCentral()
}
