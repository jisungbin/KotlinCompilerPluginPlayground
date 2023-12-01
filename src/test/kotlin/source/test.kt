package source

import kotlin.reflect.KProperty

class Value

class State {
  var value = Value()
}

inline fun <T> remember(lambda: () -> T): T = lambda()

@Suppress("NOTHING_TO_INLINE")
inline operator fun State.getValue(thisObj: State?, property: KProperty<*>): Value = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun State.setValue(thisObj: State?, property: KProperty<*>, value: Value) {
  this.value = value
}

@Suppress("UNUSED_VARIABLE")
fun main() {
  val direct = remember { State() }
  var delegation by remember { State() }
}
