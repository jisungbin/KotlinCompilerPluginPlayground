package source

class Logger {
  fun log() = Unit
}

class TestClass {
  companion object {
    val logger = Logger()
  }
}

fun main() {
  TestClass.logger.log()
}