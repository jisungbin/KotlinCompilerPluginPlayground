import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrVariable
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.types.classOrFail
import org.jetbrains.kotlin.ir.types.superTypes
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

  override fun visitVariable(declaration: IrVariable): IrStatement {
    logger(
      """
      [VARIABLE]
      name: ${declaration.name.asString()}
      type: ${declaration.type.classFqName?.asString()}
      super types: ${
        declaration.type.classOrFail
          .superTypes()
          .joinToString(transform = { it.classFqName?.asString() ?: "<unknown>" })
      }
      {PROPERTY_DELEGATE}: ${declaration.origin == IrDeclarationOrigin.PROPERTY_DELEGATE}
      """.trimIndent()
    )
    return super.visitVariable(declaration)
  }

  override fun visitLocalDelegatedProperty(declaration: IrLocalDelegatedProperty): IrStatement {
    logger(
      """
      [LocalDelegatedProperty]
      name: ${declaration.name.asString()}
      type: ${declaration.type.classFqName?.asString()}
      delegate type: ${declaration.delegate.type.classFqName?.asString()}
      delegate super types: ${
        declaration.delegate.type.classOrFail
          .superTypes()
          .joinToString(transform = { it.classFqName?.asString() ?: "<unknown>" })
      }
      """.trimIndent()
    )
    return super.visitLocalDelegatedProperty(declaration)
  }
}
