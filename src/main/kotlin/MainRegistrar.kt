import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

class MainRegistrar : CompilerPluginRegistrar() {
  override val supportsK2: Boolean get() = true
  override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
    val logger = Logger(configuration)
    IrGenerationExtension.registerExtension(IrExtension(logger))
    // FirExtensionRegistrarAdapter.registerExtension(FirCheckerRegistrar(logger))
  }
}
