package source

abstract class T {
  open val test get() = call()

  abstract fun a()
}

class TT : T() {
  override fun a() {}
}

private fun call() = 1
