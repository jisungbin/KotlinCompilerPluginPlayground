package source

@Suppress("unused")
fun test() {
  Any()
  Any()
  Any()
}

@Suppress("unused")
fun test2() {
  when (1 + 1) {
    2 -> {
      println("2")
      println("2")
      println("2")
    }
    else -> println("else")
  }
}
