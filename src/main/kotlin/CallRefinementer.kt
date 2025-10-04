import org.jetbrains.kotlin.KtSourceElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall
import org.jetbrains.kotlin.fir.extensions.FirExtensionApiInternals
import org.jetbrains.kotlin.fir.extensions.FirFunctionCallRefinementExtension
import org.jetbrains.kotlin.fir.resolve.calls.candidate.CallInfo
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.name.Name

@OptIn(FirExtensionApiInternals::class)
class CallRefinementer(session: FirSession) : FirFunctionCallRefinementExtension(session) {
  override fun intercept(callInfo: CallInfo, symbol: FirNamedFunctionSymbol): CallReturnType? {
    return null
  }

  override fun transform(call: FirFunctionCall, originalSymbol: FirNamedFunctionSymbol): FirFunctionCall {
    TODO("Not yet implemented")
  }

  override fun ownsSymbol(symbol: FirRegularClassSymbol): Boolean {
    TODO("Not yet implemented")
  }

  override fun anchorElement(symbol: FirRegularClassSymbol): KtSourceElement {
    TODO("Not yet implemented")
  }

  override fun restoreSymbol(call: FirFunctionCall, name: Name): FirRegularClassSymbol? {
    TODO("Not yet implemented")
  }
}
