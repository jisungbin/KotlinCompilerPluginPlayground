import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.IrVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid

class IrTestExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    moduleFragment.acceptChildrenVoid(
      object : IrVisitorVoid() {
        override fun visitElement(element: IrElement) {
          element.acceptChildrenVoid(this)
        }

        override fun visitClass(declaration: IrClass) {
          declaration.acceptChildrenVoid(this)
        }
      }
    )
  }
}
