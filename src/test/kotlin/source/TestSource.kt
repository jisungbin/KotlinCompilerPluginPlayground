package source

fun entry() {
  lambda {
    "Hello, world!"
  }
}

fun lambda(block: () -> String) {
  println(block())
}