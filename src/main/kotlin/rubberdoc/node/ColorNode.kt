package rubberdoc.node

import rubberdoc.node.signature.Signature

data class ColorNode(
  val signature: Signature,
  val colors: List<Color>,
)

data class Color(
  val signature: Signature,
  val value: String,
)
