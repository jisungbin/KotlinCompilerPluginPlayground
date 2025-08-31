package source

import androidx.compose.runtime.Composable
import kotlin.random.Random

open class Test {
  @Composable open fun A(): Int {
    if (Random.nextBoolean()) return 2
    return 1
  }
}

object AA : Test() {
  @Composable override fun A(): Int {
    if (Random.nextBoolean()) return 3
    val a = super.A()
    return a
  }
}

@Composable fun a() {
  AA.A()
}
