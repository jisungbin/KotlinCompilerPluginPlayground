import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName

class IrExtension(private val logger: Logger) : IrGenerationExtension {
  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    val functions = pluginContext.referenceFunctions(CallableId.fromFqName(MUTABLE_LIST_ADD_FQN))
    logger("Found ${functions.size} functions.")
  }

  private fun CallableId.Companion.fromFqName(fqName: FqName): CallableId =
    CallableId(packageName = fqName.parent(), callableName = fqName.shortName())

  companion object {
    val MUTABLE_LIST_ADD_FQN = FqName("kotlin.collections.MutableList.add")
  }
}
