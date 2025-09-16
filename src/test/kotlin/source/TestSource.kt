package source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import kotlin.reflect.KProperty

object MaterialTheme {
  val background: Int = 0
}

fun interface ThemeToken<T> {

  @Composable
  @ReadOnlyComposable
  fun MaterialTheme.resolve(): T

  @Composable
  @ReadOnlyComposable
  operator fun getValue(thisRef: Any?, property: KProperty<*>) = MaterialTheme.resolve()
}

@get:Composable
val background by ThemeToken { background }