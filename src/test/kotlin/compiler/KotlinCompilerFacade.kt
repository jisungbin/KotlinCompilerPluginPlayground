package compiler

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtilRt
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.PsiFileFactoryImpl
import com.intellij.testFramework.LightVirtualFile
import java.nio.charset.StandardCharsets
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.codegen.state.GenerationState
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.IrVerificationMode
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.config.messageCollector
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.AnalyzingUtils

class SourceFile(
  val name: String,
  val source: String,
  private val ignoreParseErrors: Boolean = false,
  val path: String = "",
) {
  fun toKtFile(project: Project): KtFile {
    val shortName = name.substring(name.lastIndexOf('/') + 1).let {
      it.substring(it.lastIndexOf('\\') + 1)
    }

    val virtualFile = object : LightVirtualFile(
      shortName,
      KotlinLanguage.INSTANCE,
      StringUtilRt.convertLineSeparators(source)
    ) {
      override fun getPath(): String = "${this@SourceFile.path}/$name"
    }

    virtualFile.charset = StandardCharsets.UTF_8
    val factory = PsiFileFactory.getInstance(project) as PsiFileFactoryImpl
    val ktFile = factory.trySetupPsiForFile(
      virtualFile, KotlinLanguage.INSTANCE, true, false
    ) as KtFile

    if (!ignoreParseErrors) AnalyzingUtils.checkForSyntacticErrors(ktFile)
    return ktFile
  }
}

interface AnalysisResult {
  data class Diagnostic(
    val factoryName: String,
    val textRanges: List<TextRange>,
  )

  val files: List<KtFile>
  val diagnostics: Map<String, List<Diagnostic>>
}

abstract class KotlinCompilerFacade(val environment: KotlinCoreEnvironment) {
  abstract fun analyze(
    platformFiles: List<SourceFile>,
    commonFiles: List<SourceFile>,
  ): AnalysisResult

  abstract fun compileToIr(files: List<SourceFile>): IrModuleFragment
  abstract fun compile(
    platformFiles: List<SourceFile>,
    commonFiles: List<SourceFile>,
  ): GenerationState

  companion object {
    const val TEST_MODULE_NAME = "source.test-module"

    fun create(
      disposable: Disposable,
      updateConfiguration: CompilerConfiguration.() -> Unit,
      registerExtensions: Project.(CompilerConfiguration) -> Unit,
    ): KotlinCompilerFacade {
      val configuration = CompilerConfiguration().apply {
        put(CommonConfigurationKeys.MODULE_NAME, TEST_MODULE_NAME)
        put(JVMConfigurationKeys.IR, true)
        put(CommonConfigurationKeys.USE_FIR, true)
        put(CommonConfigurationKeys.VERIFY_IR, IrVerificationMode.ERROR)
        put(CommonConfigurationKeys.ENABLE_IR_VISIBILITY_CHECKS, true)
        put(JVMConfigurationKeys.JVM_TARGET, JvmTarget.JVM_22)
        messageCollector = TestMessageCollector
        updateConfiguration()
      }

      val environment = KotlinCoreEnvironment.createForTests(
        parentDisposable = disposable,
        initialConfiguration = configuration,
        extensionConfigs = EnvironmentConfigFiles.JVM_CONFIG_FILES,
      )
      environment.project.registerExtensions(configuration)

      return K2CompilerFacade(environment)
    }
  }
}

private object TestMessageCollector : MessageCollector {
  override fun clear() {}

  override fun report(
    severity: CompilerMessageSeverity,
    message: String,
    location: CompilerMessageSourceLocation?,
  ) {
    println(message)
  }

  override fun hasErrors(): Boolean = false
}
