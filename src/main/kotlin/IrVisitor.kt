import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.dump

class IrTestExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    logger(moduleFragment.dump())
//    moduleFragment.transformChildrenVoid(
//      object : IrElementTransformerVoid() {
//        override fun visitCall(expression: IrCall): IrExpression {
//          of )_
//
//          if (expression.origin == IrStatementOrigin.GET_PROPERTY) {
//            println(expression)
//          }
//
//          return super.visitCall(expression)
//        }
//      },
//    )
//  }
  }
}
