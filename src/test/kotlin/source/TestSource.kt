package source

import androidx.compose.runtime.Composable
import kotlin.reflect.KProperty

class FooInline

@Composable
inline operator fun FooInline.getValue(thisRef: Any?, property: KProperty<*>) = 0

@Composable fun Test(foo: FooInline): Int {
  val value by foo
  return value
}