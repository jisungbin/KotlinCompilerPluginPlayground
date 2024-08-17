
import compiler.AbstractCompilerTest
import compiler.FirAnalysisResult
import kotlin.test.Test
import org.jetbrains.kotlin.fir.renderer.FirRenderer
import org.jetbrains.kotlin.ir.util.render
import source.source

class K2Test : AbstractCompilerTest() {
  @Test
  fun playground() {
    println("----- FIR -----")
    (analyze(listOf(source("TestSource.kt"))) as FirAnalysisResult).firResult.outputs.forEach { output ->
      output.fir.forEach { file ->
        println(FirRenderer.withResolvePhase().renderElementWithTypeAsString(file))
      }
    }

    println()
    println()
    println()

    println("----- IR -----")
    println(compileToIr(listOf(source("TestSource.kt"))).render())
  }
}
