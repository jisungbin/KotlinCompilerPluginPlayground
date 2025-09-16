import androidx.compose.compiler.plugins.kotlin.ComposePluginRegistrar
import com.tschuchort.compiletesting.KotlinCompilation
import kotlin.test.Test
import source.source

class K2Test {
  @Test fun playground() {
    KotlinCompilation().apply {
      sources = listOf(source("TestSource.kt"))
      compilerPluginRegistrars = listOf(ComposePluginRegistrar(), MainRegistrar())
      jvmTarget = "21"
      inheritClassPath = true
      messageOutputStream = System.out
    }.compile()
  }
}
