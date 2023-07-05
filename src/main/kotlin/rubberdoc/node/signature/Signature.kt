package rubberdoc.node.signature

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import java.io.File

data class Signature(
  val name: String,
  val annotations: List<AnnotationSpec>,
  val modifiers: List<KModifier>,
  val arguments: List<Signature>?,
  val returnType: ClassName?,
  val description: String,
  val containingFile: File?,
  val fileLocation: FileLocation,
)
