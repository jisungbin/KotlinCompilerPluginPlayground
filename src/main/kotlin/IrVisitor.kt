@file:Suppress("unused", "CanBeParameter")

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import rubberdoc.visitor.color.ColorDrVisitor
import team.duckie.quackquack.util.backend.kotlinc.Logger

class IrVisitor(
  private val context: IrPluginContext,
  private val logger: Logger,
) : IrElementVisitorVoid {
  private val colorVisitor = ColorDrVisitor(context, logger)

  override fun visitModuleFragment(declaration: IrModuleFragment) {
    declaration.files.forEach { file ->
      file.accept(this, null)
    }
  }

  override fun visitFile(declaration: IrFile) {
    declaration.declarations.forEach { item ->
      item.accept(colorVisitor, null)
    }
  }
}