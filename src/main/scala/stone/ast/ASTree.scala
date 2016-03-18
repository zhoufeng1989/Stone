package stone.ast

import stone._

/**
 * Created by zhoufeng on 16/2/23.
 */
class ASTree

class ASTLeaf extends ASTree

class ASTList(val children: List[ASTree]) extends ASTree

case class NumberLiteral(token: NumToken) extends ASTLeaf {
  def value = token.number
}


case class Name(token: IdToken) extends ASTLeaf {
  def name = token.text
}


case class StringLiteral(token: StrToken) extends ASTLeaf {
  def value = token.text
}


case class BinaryExpr(left: ASTree, right: ASTree, op: Name) extends ASTList(List(left, op, right)) {
  def operator = op.token.text
}
