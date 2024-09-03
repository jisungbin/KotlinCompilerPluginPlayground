import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAnnotationCallChecker
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAnnotationChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.expressions.FirAnnotation
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class FirCheckerRegistrar(private val logger: Logger) : FirExtensionRegistrar() {
  override fun ExtensionRegistrarContext.configurePlugin() {
    logger("FirCheckerRegistrar.configurePlugin")
    +(FirAdditionalCheckersExtension.Factory { FirCallChecker(it, logger) })
  }
}

private class FirCallChecker(
  session: FirSession,
  private val logger: Logger,
) : FirAdditionalCheckersExtension(session) {
  init {
    logger("FirCallChecker.init")
  }

  override val expressionCheckers: ExpressionCheckers = object : ExpressionCheckers() {
    override val annotationCheckers: Set<FirAnnotationChecker> = setOf(
      object : FirAnnotationChecker(MppCheckerKind.Common) {
        override fun check(expression: FirAnnotation, context: CheckerContext, reporter: DiagnosticReporter) {
          logger("FirAnnotationChecker: $expression")
        }
      },
    )

    override val annotationCallCheckers: Set<FirAnnotationCallChecker> = setOf(
      object : FirAnnotationCallChecker(MppCheckerKind.Common) {
        override fun check(expression: FirAnnotationCall, context: CheckerContext, reporter: DiagnosticReporter) {
          logger("FirAnnotationCallChecker: $expression")
        }
      },
    )
  }
}
