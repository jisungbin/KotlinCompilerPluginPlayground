@file:Suppress("unused", "UNUSED_VARIABLE", "RedundantUnitExpression")
@file:OptIn(UnsafeCastFunction::class, ObsoleteDescriptorBasedAPI::class)

package rubberdoc.visitor.color

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.jvm.getGetterField
import org.jetbrains.kotlin.backend.jvm.ir.psiElement
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.declarations.impl.IrFieldImpl
import org.jetbrains.kotlin.ir.descriptors.toIrBasedKotlinType
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl
import org.jetbrains.kotlin.ir.util.companionObject
import org.jetbrains.kotlin.ir.util.constructors
import org.jetbrains.kotlin.ir.util.defaultConstructor
import org.jetbrains.kotlin.ir.util.parentAsClass
import org.jetbrains.kotlin.ir.util.primaryConstructor
import org.jetbrains.kotlin.ir.util.properties
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction
import org.jetbrains.kotlin.utils.addToStdlib.cast
import rubberdoc.node.Color
import rubberdoc.node.ColorNode
import rubberdoc.node.signature.Signature
import rubberdoc.visitor.utils.getAnnotationSpecs
import rubberdoc.visitor.utils.getIOFileAndFileLocationPair
import rubberdoc.visitor.utils.getKModifiers
import rubberdoc.visitor.utils.toSignature
import team.duckie.quackquack.util.backend.kotlinc.Logger

class ColorDrVisitor(
  private val context: IrPluginContext,
  private val logger: Logger,
) : IrElementVisitorVoid {
  override fun visitClass(declaration: IrClass) {
    with(declaration) {
      val name = name.asString()
      if (name != "QuackColor") return

      val annotations = getAnnotationSpecs()
      val modifiers = getKModifiers()

      val constructor = defaultConstructor ?: primaryConstructor
      val arguments = constructor?.valueParameters?.map(IrValueParameter::toSignature)

      val (containingFile, fileLocation) = getIOFileAndFileLocationPair()

      val colors: List<Color> =
        companionObject()?.let { companion ->
          val colorableProperties = companion.properties.filter { property ->
            property.visibility.isPublicAPI
          }.toList()
          colorableProperties.map { property ->
            val getter = property.getter!!
            val returnStatement =
              getter.body!!.statements.last { statement ->
                statement is IrReturnImpl
              } as IrReturnImpl
            val returnType = returnStatement.value.type.toIrBasedKotlinType()
            val getterField = getter.getGetterField()!!
            val getterPsi = getter.psiElement
            val getterFieldPsi = getterField.psiElement
            val backingFieldPsi = property.backingField!!.psiElement
            val field = property.backingField!!.cast<IrFieldImpl>()
            val parent = field.symbol.owner.parentAsClass
            val constructors = parent.constructors.toList()
            Unit
          }
          emptyList()
        } ?: emptyList()

      val node = ColorNode(
        signature = Signature(
          name = name,
          annotations = annotations,
          modifiers = modifiers,
          arguments = arguments,
          returnType = null,
          description = "TODO",
          containingFile = containingFile,
          fileLocation = fileLocation,
        ),
        colors = colors,
      )
    }
  }
}
