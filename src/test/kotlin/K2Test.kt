import com.tschuchort.compiletesting.KotlinCompilation
import kotlin.test.Test
import source.source

class K2Test {
  @Test fun playground() {
    KotlinCompilation().apply {
      sources = listOf(source("TestSource.kt"))
      compilerPluginRegistrars = listOf(MainRegistrar())
      inheritClassPath = true
      messageOutputStream = System.out
    }.compile()
  }
}
