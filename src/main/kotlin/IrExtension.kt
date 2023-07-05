import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import team.duckie.quackquack.util.backend.kotlinc.Logger

class IrExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    val visitor = IrVisitor(context = pluginContext, logger = logger)
    moduleFragment.accept(visitor, null)
  }
}