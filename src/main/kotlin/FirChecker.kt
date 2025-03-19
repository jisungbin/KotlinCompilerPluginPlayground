import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirSimpleFunctionChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class FirCheckerRegistrar(@Suppress("unused") private val logger: Logger) : FirExtensionRegistrar() {
  override fun ExtensionRegistrarContext.configurePlugin() {
    +::FileValidatorExtension
  }
}

class FileValidatorExtension(session: FirSession) : FirAdditionalCheckersExtension(session) {
  override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
    override val simpleFunctionCheckers: Set<FirSimpleFunctionChecker> =
      setOf(
        object : FirSimpleFunctionChecker(MppCheckerKind.Common) {
          override fun check(declaration: FirSimpleFunction, context: CheckerContext, reporter: DiagnosticReporter) {
            val name = declaration.name.asString()
            println(name)
          }
        },
      )
  }
}
