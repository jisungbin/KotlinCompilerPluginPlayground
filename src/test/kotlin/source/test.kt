package source

val helloWorld: Unit
  get() {
    A.b.print("A")
  }

object A { val b = B }
object B
fun B.print(a: String) = println(a)