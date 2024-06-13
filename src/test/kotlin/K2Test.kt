import source.source
import kotlin.test.Test

class K2Test : AbstractK2CompilerTest() {
  @Test
  fun playground() {
    compileToIr(listOf(source("TestSource.kt")))
  }
}
