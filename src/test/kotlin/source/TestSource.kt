package source

object Test {
  fun lambdaTest(value: Any) { print(value) }
}

fun test() {
  1.also(Test::lambdaTest)

  fun lambda(value: Any) { print(value) }

  1.also(::lambda)
}
