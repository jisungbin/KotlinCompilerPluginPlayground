package rubberdoc.node

import rubberdoc.node.signature.Signature

data class TopLevelPropNode(
  val signature: Signature,
  val properties: List<Signature>,
)
