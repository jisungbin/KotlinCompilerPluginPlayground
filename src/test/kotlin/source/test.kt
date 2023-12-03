package source

fun main() {
  val result = mutableListOf<Int>()
  run {
    val a = 2.also { b: Any ->
      result.add(b as Int)
    }
  }
  println(result)
}
