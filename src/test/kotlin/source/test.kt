package source

@JvmInline
value class Lambda(private val action: (value: Any) -> Unit) : Function1<Any, Unit> by action

fun main() {
  val printer = Lambda { value -> println(value) }
  printer(0)
  printer.invoke(1)
}

//object Test {
//  var a = 0
//}
//
//fun main() {
//  println(Test.a)
//}
