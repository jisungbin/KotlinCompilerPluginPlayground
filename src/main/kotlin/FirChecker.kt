import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirSimpleFunctionChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction
import org.jetbrains.kotlin.fir.extensions.FirExtensionApiInternals
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class FirCheckerRegistrar(@Suppress("unused") private val logger: Logger) : FirExtensionRegistrar() {
  @OptIn(FirExtensionApiInternals::class)
  override fun ExtensionRegistrarContext.configurePlugin() {
//    +::FileValidatorExtension
    +::CallRefinementer
  }
}

class FileValidatorExtension(session: FirSession) : FirAdditionalCheckersExtension(session) {
  override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
    override val simpleFunctionCheckers: Set<FirSimpleFunctionChecker> =
      setOf(
        object : FirSimpleFunctionChecker(MppCheckerKind.Common) {
          context(context: CheckerContext, reporter: DiagnosticReporter)
          override fun check(declaration: FirSimpleFunction) {
            val name = declaration.name.asString()
            println(name)
          }
        },
      )
  }
}
