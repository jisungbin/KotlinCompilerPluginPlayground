@file:Suppress("unused", "MayBeConstant")

package aosp

import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.interpreter.hasAnnotation
import org.jetbrains.kotlin.ir.util.isLambda
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

object KtxNameConventions {
  val COMPOSER = Name.identifier("composer")
  val COMPOSER_PARAMETER = Name.identifier("\$composer")
  val CHANGED_PARAMETER = Name.identifier("\$changed")
  val FORCE_PARAMETER = Name.identifier("\$force")
  val STABILITY_FLAG = Name.identifier("\$stable")
  val STABILITY_PROP_FLAG = Name.identifier("\$stableprop")
  val DEFAULT_PARAMETER = Name.identifier("\$default")
  val JOINKEY = Name.identifier("joinKey")
  val STARTRESTARTGROUP = Name.identifier("startRestartGroup")
  val ENDRESTARTGROUP = Name.identifier("endRestartGroup")
  val UPDATE_SCOPE = Name.identifier("updateScope")
  val SOURCEINFORMATION = "sourceInformation"
  val SOURCEINFORMATIONMARKERSTART = "sourceInformationMarkerStart"
  val IS_TRACE_IN_PROGRESS = "isTraceInProgress"
  val TRACE_EVENT_START = "traceEventStart"
  val TRACE_EVENT_END = "traceEventEnd"
  val SOURCEINFORMATIONMARKEREND = "sourceInformationMarkerEnd"
  val UPDATE_CHANGED_FLAGS = "updateChangedFlags"
  val CURRENTMARKER = Name.identifier("currentMarker")
  val ENDTOMARKER = Name.identifier("endToMarker")
}

inline fun <T> includeFileNameInExceptionTrace(file: IrFile, body: () -> T): T {
  try {
    return body()
  } catch (e: ProcessCanceledException) {
    throw e
  } catch (e: Exception) {
    throw Exception("IR lowering failed at: ${file.name}", e)
  }
}

fun IrAnnotationContainer.hasComposableAnnotation() =
  hasAnnotation(FqName("androidx.compose.runtime.Composable"))


fun IrExpression.unwrapLambda() =
  when {
    this is IrBlock && origin.isLambdaBlockOrigin -> {
      (statements.lastOrNull() as? IrFunctionReference)?.symbol
    }
    this is IrFunctionExpression -> {
      function.symbol
    }
    else -> null
  }

private val IrStatementOrigin?.isLambdaBlockOrigin: Boolean
  get() =
    isLambda ||
      this == IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE ||
      this == IrStatementOrigin.SUSPEND_CONVERSION