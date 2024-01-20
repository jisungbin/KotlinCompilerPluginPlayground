@file:Suppress("DEPRECATION")

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import util.Logger

@AutoService(ComponentRegistrar::class)
class MainRegistrar : ComponentRegistrar {
  override fun registerProjectComponents(
    project: MockProject,
    configuration: CompilerConfiguration,
  ) {
    val logger = Logger(configuration = configuration)

    project.extensionArea
      .getExtensionPoint(IrGenerationExtension.extensionPointName)
      .registerExtension(
        object : IrGenerationExtension {
          override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
            logger("FIRST IrGenerationExtension")
          }
        },
        LoadingOrder.FIRST,
        project,
      )

    project.extensionArea
      .getExtensionPoint(IrGenerationExtension.extensionPointName)
      .registerExtension(
        object : IrGenerationExtension {
          override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
            logger("LAST IrGenerationExtension")
          }
        },
        LoadingOrder.LAST,
        project,
      )
  }
}
