package source

class AS {
  val a = Any()

  companion object {
    val b = Any()
  }
}

fun test() {
  val instance = AS()
  println(instance.a)
  println(AS.b)
}
