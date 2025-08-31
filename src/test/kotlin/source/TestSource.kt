package source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import kotlin.random.Random

object AA {
  @Composable fun A(): Int {
    if (Random.nextBoolean()) return 3
    println("recomposeScope is $currentRecomposeScope")
    return 1
  }
}

@Composable fun a() {
  AA.A()
}
