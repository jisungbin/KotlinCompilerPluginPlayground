package source

import kotlin.reflect.KProperty

class Value

interface State {
  val value: Value
}

interface MutableState : State {
  override var value: Value
}

@Suppress("TestFunctionName")
fun State(): State = object : State {
  override val value = Value()
}

@Suppress("TestFunctionName")
fun MutableState(): MutableState = object : MutableState {
  override var value = Value()
}

inline fun <T> remember(lambda: () -> T): T = lambda()

@Suppress("NOTHING_TO_INLINE")
inline operator fun State.getValue(thisObj: MutableState?, property: KProperty<*>): Value = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun MutableState.setValue(
  thisObj: MutableState?,
  property: KProperty<*>,
  value: Value,
) {
  this.value = value
}

// FUN name:main visibility:public modality:FINAL <> () returnType:kotlin.Unit
//  annotations:
//    Suppress(names = ['UNUSED_VARIABLE'])
//  BLOCK_BODY
//    VAR name:direct type:source.State [val]
//      CALL 'public final fun remember <T> (lambda: kotlin.Function0<T of source.remember>): T of source.remember [inline] declared in source' type=source.State origin=null
//        <T>: source.State
//        lambda: FUN_EXPR type=kotlin.Function0<source.State> origin=LAMBDA
//          FUN LOCAL_FUNCTION_FOR_LAMBDA name:<anonymous> visibility:local modality:FINAL <> () returnType:source.State
//            BLOCK_BODY
//              RETURN type=kotlin.Nothing from='local final fun <anonymous> (): source.State declared in source.main'
//                CONSTRUCTOR_CALL 'public constructor <init> () [primary] declared in source.State' type=source.State origin=null
//    LOCAL_DELEGATED_PROPERTY name:delegation type:source.Value flags:var
//      VAR PROPERTY_DELEGATE name:delegation$delegate type:source.State [val]
//        CALL 'public final fun remember <T> (lambda: kotlin.Function0<T of source.remember>): T of source.remember [inline] declared in source' type=source.State origin=null
//          <T>: source.State
//          lambda: FUN_EXPR type=kotlin.Function0<source.State> origin=LAMBDA
//            FUN LOCAL_FUNCTION_FOR_LAMBDA name:<anonymous> visibility:local modality:FINAL <> () returnType:source.State
//              BLOCK_BODY
//                RETURN type=kotlin.Nothing from='local final fun <anonymous> (): source.State declared in source.main'
//                  CONSTRUCTOR_CALL 'public constructor <init> () [primary] declared in source.State' type=source.State origin=null
//      FUN DELEGATED_PROPERTY_ACCESSOR name:<get-delegation> visibility:local modality:FINAL <> () returnType:source.Value
//        BLOCK_BODY
//          RETURN type=kotlin.Nothing from='local final fun <get-delegation> (): source.Value declared in source.main'
//            CALL 'public final fun getValue (thisObj: source.State?, property: kotlin.reflect.KProperty<*>): source.Value [inline,operator] declared in source' type=source.Value origin=null
//              $receiver: GET_VAR 'val delegation$delegate: source.State [val] declared in source.main' type=source.State origin=null
//              thisObj: CONST Null type=kotlin.Nothing? value=null
//              property: LOCAL_DELEGATED_PROPERTY_REFERENCE 'var delegation: source.Value by (...)' delegate='val delegation$delegate: source.State [val] declared in source.main' getter='local final fun <get-delegation> (): source.Value declared in source.main' setter='local final fun <set-delegation> (value: source.Value): kotlin.Unit declared in source.main' type=kotlin.reflect.KMutableProperty0<source.Value> origin=PROPERTY_REFERENCE_FOR_DELEGATE
//      FUN DELEGATED_PROPERTY_ACCESSOR name:<set-delegation> visibility:local modality:FINAL <> (value:source.Value) returnType:kotlin.Unit
//        VALUE_PARAMETER name:value index:0 type:source.Value
//        BLOCK_BODY
//          RETURN type=kotlin.Nothing from='local final fun <set-delegation> (value: source.Value): kotlin.Unit declared in source.main'
//            CALL 'public final fun setValue (thisObj: source.State?, property: kotlin.reflect.KProperty<*>, value: source.Value): kotlin.Unit [inline,operator] declared in source' type=kotlin.Unit origin=null
//              $receiver: GET_VAR 'val delegation$delegate: source.State [val] declared in source.main' type=source.State origin=null
//              thisObj: CONST Null type=kotlin.Nothing? value=null
//              property: LOCAL_DELEGATED_PROPERTY_REFERENCE 'var delegation: source.Value by (...)' delegate='val delegation$delegate: source.State [val] declared in source.main' getter='local final fun <get-delegation> (): source.Value declared in source.main' setter='local final fun <set-delegation> (value: source.Value): kotlin.Unit declared in source.main' type=kotlin.reflect.KMutableProperty0<source.Value> origin=PROPERTY_REFERENCE_FOR_DELEGATE
//              value: GET_VAR 'value: source.Value declared in source.main.<set-delegation>' type=source.Value origin=null
@Suppress("UNUSED_VARIABLE")
fun main() {
  val direct = remember { State() }
  var delegation by remember { MutableState() }
}
