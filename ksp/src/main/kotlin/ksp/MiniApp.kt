package ksp

// Define the custom annotation
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class MiniApp(val name: String)