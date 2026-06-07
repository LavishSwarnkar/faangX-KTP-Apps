pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

rootProject.name = "faangX-KTP-Apps"
include("ksp")
include("apps")

// :catalog is a local instructor/dev tool and contains reference SOLUTIONS in its demo/ files.
// It is never published (no maven-publish) and nothing depends on it, so it can't leak into the
// distributed `mini-apps` artifact. We additionally exclude it from CI/JitPack builds (pass
// -PexcludeCatalog, see jitpack.yml) so solutions are never even compiled there.
if (!providers.gradleProperty("excludeCatalog").isPresent) {
    include("catalog")
}