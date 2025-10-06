package source

context(a: Test)
fun test(): Any = a

object Test {
  val a = test()
}
