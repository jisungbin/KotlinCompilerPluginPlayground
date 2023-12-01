import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import util.Logger

@AutoService(CompilerPluginRegistrar::class)
class MainRegistrar : CompilerPluginRegistrar() {
  override val supportsK2 = false
  override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
    val logger = Logger(configuration = configuration)
    IrGenerationExtension.registerExtension(IrExtension(logger = logger))
  }
}
