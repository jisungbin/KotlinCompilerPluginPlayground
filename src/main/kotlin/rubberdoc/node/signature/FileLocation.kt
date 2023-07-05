package rubberdoc.node.signature

data class FileLocation(
  val startLine: Int,
  val startColumn: Int,
  val endLine: Int,
  val endColumn: Int,
)
