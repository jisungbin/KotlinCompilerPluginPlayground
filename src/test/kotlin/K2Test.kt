import compiler.AbstractCompilerTest
import kotlin.test.Test
import org.jetbrains.kotlin.ir.util.dump
import source.source

class K2Test : AbstractCompilerTest() {
  @Test
  fun playground() {
    println(compileToIr(listOf(source("TestSource.kt"))).dump())
  }
}
