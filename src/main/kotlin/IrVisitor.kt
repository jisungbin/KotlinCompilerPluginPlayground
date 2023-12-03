import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import util.Logger
import util.dumpSrc

class IrExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    val visitor = IrVisitor(context = pluginContext, logger = logger)
    moduleFragment.accept(visitor, null)
  }
}

private class IrVisitor(
  @Suppress("unused") private val context: IrPluginContext,
  private val logger: Logger,
) : IrElementTransformerVoidWithContext() {
  override fun visitFileNew(declaration: IrFile): IrFile {
    logger("dump: ${declaration.dump()}")
    logger("\n\n")
    logger("dumpSrc: ${declaration.dumpSrc()}")
    logger("\n\n")
    logger("dumpKotlinLike: ${declaration.dumpKotlinLike()}")
    return super.visitFileNew(declaration)
  }
}
