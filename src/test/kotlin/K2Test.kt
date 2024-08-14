import compiler.AbstractCompilerTest
import kotlin.test.Test
import source.source

class K2Test : AbstractCompilerTest() {
  @Test
  fun playground() {
    analyze(listOf(source("TestSource.kt")))
  }
}
