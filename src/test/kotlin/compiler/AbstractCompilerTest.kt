package compiler

import FirCheckerRegistrar
import IrExtension
import Logger
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.io.FileUtil
import com.intellij.util.PathUtil
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.cli.jvm.config.configureJdkClasspathRoots
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.compiler.plugin.registerExtensionsForTest
import org.jetbrains.kotlin.config.AnalysisFlag
import org.jetbrains.kotlin.config.AnalysisFlags
import org.jetbrains.kotlin.config.ApiVersion
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlin.config.LanguageVersion
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment

abstract class AbstractCompilerTest {
  companion object {
    private fun File.applyExistenceCheck(): File = apply {
      if (!exists()) throw NoSuchFileException(this)
    }

    private val homeDir: String = run {
      val userDir = System.getProperty("user.dir")
      val dir = File(userDir ?: ".")
      val path = FileUtil.toCanonicalPath(dir.absolutePath)
      File(path).applyExistenceCheck().absolutePath
    }

    val defaultClassPath by lazy { listOf(Classpath.kotlinStdlibJar()) }
    val defaultClassLoader = this::class.java.classLoader
  }

  private val testRootDisposable = Disposer.newDisposable()

  @BeforeTest
  fun setSystemProperties() {
    System.setProperty("idea.home", homeDir)
    System.setProperty("user.dir", homeDir)
    System.setProperty("idea.ignore.disabled.plugins", "true")
  }

  @AfterTest
  fun disposeTestRootDisposable() {
    Disposer.dispose(testRootDisposable)
  }

  @OptIn(ExperimentalCompilerApi::class)
  private fun createCompilerFacade(additionalPaths: List<File> = emptyList()) =
    KotlinCompilerFacade.create(
      disposable = testRootDisposable,
      updateConfiguration = {
        val languageVersion = LanguageVersion.KOTLIN_2_0
        val analysisFlags: Map<AnalysisFlag<*>, Any?> = mapOf(
          AnalysisFlags.allowUnstableDependencies to true,
          AnalysisFlags.skipPrereleaseCheck to true
        )
        languageVersionSettings = LanguageVersionSettingsImpl(
          languageVersion,
          ApiVersion.createByLanguageVersion(languageVersion),
          analysisFlags
        )

        addJvmClasspathRoots(additionalPaths)
        addJvmClasspathRoots(defaultClassPath)

        if (!getBoolean(JVMConfigurationKeys.NO_JDK) && get(JVMConfigurationKeys.JDK_HOME) == null) {
          put(JVMConfigurationKeys.JDK_HOME, File(System.getProperty("java.home")!!))
        }
        configureJdkClasspathRoots()
      },
      registerExtensions = { configuration ->
        val logger = Logger(configuration)
        registerExtensionsForTest(this, configuration) {
          FirExtensionRegistrarAdapter.registerExtension(FirCheckerRegistrar(logger))
        }
        IrGenerationExtension.registerExtension(this, IrExtension(logger))
      }
    )

  protected fun analyze(
    platformSources: List<SourceFile>,
    commonSources: List<SourceFile> = emptyList(),
    additionalPaths: List<File> = emptyList(),
  ): AnalysisResult =
    createCompilerFacade(additionalPaths).analyze(platformSources, commonSources)

  protected fun compileToIr(sourceFiles: List<SourceFile>, additionalPaths: List<File> = emptyList()): IrModuleFragment =
    createCompilerFacade(additionalPaths).compileToIr(sourceFiles)
}

object Classpath {
  fun kotlinStdlibJar() = jarFor<Unit>()
  inline fun <reified T> jarFor() = File(PathUtil.getJarPathForClass(T::class.java))
}