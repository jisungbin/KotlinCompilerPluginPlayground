package source

import kotlin.properties.Delegates


fun main() {
//  var a by Delegates.notNull<Int>()
//  a = 1
//  println(a)

  val a = Any()
  ::a
}
