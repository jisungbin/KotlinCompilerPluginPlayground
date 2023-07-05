@file:Suppress("unused")

package source

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@JvmInline
value class Color(private val value: Long) {
  companion object {
    val UnspecifiedValue = Color(0)
  }
}

@JvmInline
value class QuackColor(private val value: Color) : ReadOnlyProperty<Any, Color> {
  companion object {
    val DuckieOrange = QuackColor(Color(0xFFFF8300))
    val UnspecifiedColor = QuackColor(Color.UnspecifiedValue)
    val Alert = QuackColor(Color(0xFFFF2929))
    val Success = QuackColor(Color(0xFF02B288))
    val Dimmed = QuackColor(Color(0x99000000))
    val Gray1 = QuackColor(Color(0xFF666666))
    val Gray2 = QuackColor(Color(0xFFA8A8A8))
    val Gray3 = QuackColor(Color(0xFFEFEFEF))
    val Gray4 = QuackColor(Color(0xFFF6F6F6))
    val Black = QuackColor(Color(0xFF222222))
    val White = QuackColor(Color(0xFFFFFFFF))
  }

  override fun getValue(thisRef: Any, property: KProperty<*>): Color = value
}
