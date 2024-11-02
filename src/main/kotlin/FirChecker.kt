import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class FirCheckerRegistrar(@Suppress("unused") private val logger: Logger) : FirExtensionRegistrar() {
  override fun ExtensionRegistrarContext.configurePlugin() {
    +::FileValidatorExtension
  }
}

class FileValidatorExtension(session: FirSession) : FirAdditionalCheckersExtension(session) {
  override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
    // override val fileCheckers: Set<FirFileChecker> = setOf(FileValidator)
  }
}

//private object FileValidator : FirFileChecker(MppCheckerKind.Common) {
//  override fun check(declaration: FirFile, context: CheckerContext, reporter: DiagnosticReporter) {
//    declaration.validate()
//  }
//}
