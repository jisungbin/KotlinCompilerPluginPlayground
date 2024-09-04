package source

object TestObj {
  fun lambdaTest(value: Any) { print(value) }
}

fun test() {
  1.also(TestObj::lambdaTest)

  fun lambda(value: Any) { print(value) }

  1.also(::lambda)
}
