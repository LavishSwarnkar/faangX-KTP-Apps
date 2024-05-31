package ksp

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class MiniApp(
    val name: String,
    val repoPath: String,
    val paramNames: String = "",
    val supportsParentScroll: Boolean = true
)