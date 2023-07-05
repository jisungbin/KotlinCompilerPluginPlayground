package rubberdoc.node

import rubberdoc.node.signature.Signature

data class ClassNode(
  val signature: Signature,
  val properties: List<Signature>,
  val functions: List<Signature>,
)
