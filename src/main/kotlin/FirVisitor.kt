import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionApiInternals
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.MemberGenerationContext
import org.jetbrains.kotlin.fir.renderer.FirRenderer
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.Name

class FirCheckerRegistrar(@Suppress("unused") private val logger: Logger) : FirExtensionRegistrar() {
  @OptIn(FirExtensionApiInternals::class)
  override fun ExtensionRegistrarContext.configurePlugin() {
//    +::CallRefinementer
//    +::DeclGenerator
    +FileValidatorExtension.Factory(logger)
  }
}

class DeclGenerator(session: FirSession) : FirDeclarationGenerationExtension(session) {
  override fun generateProperties(callableId: CallableId, context: MemberGenerationContext?): List<FirPropertySymbol> {
    println(callableId.toString())
    return super.generateProperties(callableId, context)
  }

  override fun getCallableNamesForClass(classSymbol: FirClassSymbol<*>, context: MemberGenerationContext): Set<Name> {
    return setOf(Name.identifier("test"))
  }
}

class FileValidatorExtension(session: FirSession, logger: Logger) : FirAdditionalCheckersExtension(session) {
  override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
    override val fileCheckers: Set<FirFileChecker> =
      setOf(
        object : FirFileChecker(MppCheckerKind.Common) {
          context(context: CheckerContext, _: DiagnosticReporter)
          override fun check(declaration: FirFile) {
//            logger(declaration.renderWithType())
            logger(FirRenderer.withResolvePhase().renderElementWithTypeAsString(declaration))
          }
        }
      )
  }

  class Factory(private val logger: Logger) : FirAdditionalCheckersExtension.Factory {
    override fun create(session: FirSession): FirAdditionalCheckersExtension = FileValidatorExtension(session, logger)
  }
}
