package rubberdoc.visitor.utils

import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.ir.util.parentAsClass

/** 주어진 어노테이션의 fqn을 조회하여 반환하고, 만약 조회에 실패했다면 공백을 반환합니다. */
fun IrConstructorCall.toFqnStringOrEmpty() =
  symbol.owner.parentAsClass.fqNameWhenAvailable?.asString().orEmpty()
