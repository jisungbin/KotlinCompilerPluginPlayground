package source

fun test() {
  fun a(a: Any) = Unit

  1.also(::print)
  1.also(::a)
}
