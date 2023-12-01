package source

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class TestDelegator : ReadOnlyProperty<Any?, String> {
  val value = "Hello, World!"
  override fun getValue(thisRef: Any?, property: KProperty<*>) = value
}

fun main() {
  val delegator = TestDelegator()
  val direct = delegator.value
  val delegation by delegator

  println("direct: $direct")
  println("delegation: $delegation")
}
