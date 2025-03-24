package source

fun testMain() {
  val lambda = { println() }

  test(
    testArgument = { println(); lambda() },
  )
}

private fun test(testArgument: () -> Unit) = testArgument()
