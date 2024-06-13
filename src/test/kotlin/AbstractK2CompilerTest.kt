@file:Suppress("UnstableApiUsage")

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.io.FileUtil
import compiler.AnalysisResult
import compiler.KotlinCompiler
import compiler.SourceFile
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.cli.jvm.config.configureJdkClasspathRoots
import org.jetbrains.kotlin.config.AnalysisFlag
import org.jetbrains.kotlin.config.AnalysisFlags
import org.jetbrains.kotlin.config.ApiVersion
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.config.LanguageVersion
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import java.io.File

abstract class AbstractK2CompilerTest {
  companion object {
    private fun File.applyExistenceCheck() =
      apply { if (!exists()) throw NoSuchFileException(this) }

    private val homeDir = run {
      val userDir = System.getProperty("user.dir")
      val dir = File(userDir ?: ".")
      val path = FileUtil.toCanonicalPath(dir.absolutePath)
      File(path).applyExistenceCheck().absolutePath
    }

    @JvmStatic
    @BeforeAll
    fun setSystemProperties() {
      System.setProperty("idea.home", homeDir)
      System.setProperty("user.dir", homeDir)
      System.setProperty("idea.ignore.disabled.plugins", "true")
    }

    private val defaultClassPath by lazy {
      System.getProperty("java.class.path")!!.split(File.pathSeparator).map(::File)
    }

    private val defaultClassPathRoots by lazy {
      defaultClassPath.filter { file ->
        !file.path.contains("robolectric") && file.extension != "xml"
      }
    }
  }

  private val testRootDisposable = Disposer.newDisposable()

  @AfterEach
  fun disposeTestRootDisposable() {
    Disposer.dispose(testRootDisposable)
  }

  private fun createCompiler(
    additionalPaths: List<File> = emptyList(),
    additionalRegisterExtensions: Project.(configuration: CompilerConfiguration) -> Unit = {},
  ) =
    KotlinCompiler.create(
      disposable = testRootDisposable,
      updateConfiguration = {
        val analysisFlags = mapOf<AnalysisFlag<*>, Any>(
          AnalysisFlags.allowUnstableDependencies to true,
          AnalysisFlags.skipPrereleaseCheck to true,
        )
        languageVersionSettings = LanguageVersionSettingsImpl(
          languageVersion = LanguageVersion.LATEST_STABLE,
          apiVersion = ApiVersion.createByLanguageVersion(LanguageVersion.LATEST_STABLE),
          analysisFlags = analysisFlags,
        )
        addJvmClasspathRoots(additionalPaths)
        addJvmClasspathRoots(defaultClassPathRoots)
        if (!getBoolean(JVMConfigurationKeys.NO_JDK) && get(JVMConfigurationKeys.JDK_HOME) == null) {
          // We need to set `JDK_HOME` explicitly to use JDK 17
          put(JVMConfigurationKeys.JDK_HOME, File(System.getProperty("java.home")!!))
        }
        configureJdkClasspathRoots()
      },
      registerExtensions = { configuration ->
        extensionArea
          .getExtensionPoint(IrGenerationExtension.extensionPointName)
          .registerExtension(IrExtension(Logger(configuration)), this)

        additionalRegisterExtensions(configuration)
      }
    )

  protected fun analyze(
    platformSources: List<SourceFile>,
    commonSources: List<SourceFile> = listOf(),
    additionalRegisterExtensions: Project.(configuration: CompilerConfiguration) -> Unit = {},
  ): AnalysisResult =
    createCompiler(additionalRegisterExtensions = additionalRegisterExtensions)
      .analyze(platformFiles = platformSources, commonFiles = commonSources)

  protected fun compileToIr(
    sourceFiles: List<SourceFile>,
    additionalPaths: List<File> = emptyList(),
    additionalRegisterExtensions: Project.(configuration: CompilerConfiguration) -> Unit = {},
  ): IrModuleFragment =
    createCompiler(
      additionalPaths = additionalPaths,
      additionalRegisterExtensions = additionalRegisterExtensions,
    )
      .compileToIr(sourceFiles)
}
