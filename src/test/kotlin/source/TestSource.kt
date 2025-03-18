package source

@Target(AnnotationTarget.TYPE)
annotation class A

private fun myCall(b: Any = Any(), a: @A () -> Unit) {}

fun main() {
  myCall {}
}
