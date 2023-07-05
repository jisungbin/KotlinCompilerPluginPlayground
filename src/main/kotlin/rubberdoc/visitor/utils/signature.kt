package rubberdoc.visitor.utils

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.util.*
import rubberdoc.node.signature.Signature
import team.duckie.quackquack.util.backend.kotlinc.unsafeClassName

fun IrClass.getKModifiers() =
  buildList {
    with(this@getKModifiers) {
      when {
        isFinalClass -> add(KModifier.FINAL)
        isOpen -> add(KModifier.OPEN)
        isAbstract -> add(KModifier.ABSTRACT)
        isSealed -> add(KModifier.SEALED)
        isInner -> add(KModifier.INNER)
        isEnumClass -> add(KModifier.ENUM)
        isAnnotationClass -> add(KModifier.ANNOTATION)
        isValue -> add(KModifier.VALUE)
        isCompanion -> add(KModifier.COMPANION)
        isData -> add(KModifier.DATA)
      }
    }
  }

fun IrValueParameter.getKModifiers() =
  buildList {
    with(this@getKModifiers) {
      when {
        isVararg -> add(KModifier.VARARG)
        isNoinline -> add(KModifier.NOINLINE)
        isCrossinline -> add(KModifier.CROSSINLINE)
      }
    }
  }

val IrClass.isSealed: Boolean
  get() = modality == Modality.SEALED

val IrClass.isOpen: Boolean
  get() = modality == Modality.OPEN

val IrClass.isAbstract: Boolean
  get() = modality == Modality.ABSTRACT

private val AnnotationSpecDumpOption =
  KotlinLikeDumpOptions(
    printRegionsPerFile = false,
    printFileName = false,
    printFilePath = false,
  )

fun IrAnnotationContainer.getAnnotationSpecs() =
  annotations.map { annotation ->
    AnnotationSpec
      .builder(ClassName.bestGuess(annotation.toFqnStringOrEmpty()))
      .apply {
        annotation.symbol.owner.valueParameters.forEachIndexed { index, argument ->
          val name = argument.name
          val value = annotation.getValueArgument(index)?.dumpKotlinLike(AnnotationSpecDumpOption).orEmpty()
          addMember("%L = %L", name, value)
        }
      }
      .build()
  }

fun IrValueParameter.toSignature(): Signature {
  val name = name.asString()
  val annotations = getAnnotationSpecs()
  val modifiers = getKModifiers()
  val returnType = type.unsafeClassName
  val description = "" // TODO: parse kdoc

  val (containingFile, fileLocation) = getIOFileAndFileLocationPair()

  return Signature(
    name = name,
    annotations = annotations,
    modifiers = modifiers,
    arguments = null,
    returnType = returnType,
    description = description,
    containingFile = containingFile,
    fileLocation = fileLocation,
  )
}
