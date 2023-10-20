@file:Suppress("unused", "CanBeParameter", "UNCHECKED_CAST")

import aosp.dumpSrc
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrProperty
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptVoid
import team.duckie.quackquack.util.backend.kotlinc.Logger

class IrVisitor(
  private val context: IrPluginContext,
  private val logger: Logger,
) : IrElementVisitorVoid {
  override fun visitModuleFragment(declaration: IrModuleFragment) {
    declaration.files.forEach { file ->
      file.acceptVoid(this)
    }
  }

  override fun visitFile(declaration: IrFile) {
    declaration.declarations.forEach { item ->
      item.acceptVoid(this)
    }
  }

  override fun visitProperty(declaration: IrProperty) {
    val body = declaration.getter!!.body!!
    body.acceptVoid(this)
  }

  override fun visitBody(body: IrBody) {
    logger(body.dump())
    logger(body.dumpSrc())
    body.statements.first().acceptVoid(
      object : IrElementVisitorVoid {
        override fun visitCall(expression: IrCall) {
          logger((expression.getValueArgument(0) as IrConst<String>).value)
        }
      }
    )
  }
}