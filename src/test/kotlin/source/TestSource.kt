package source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

object MyState : State<Any> {
  override val value: Any get() = ""
}

@Composable fun MyComposable() {
  val a: State<Any> = MyState
  val b: State<Any> = remember { MyState }
  val c: State<Any> = rememberSaveable { MyState }
}
