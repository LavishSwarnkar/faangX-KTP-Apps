package ksp

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class MiniApp(
    val name: String,
    val paramNames: String = ""
)