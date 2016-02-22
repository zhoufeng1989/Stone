package stone.ast

import stone._

/**
 * Created by zhoufeng on 16/2/23.
 */
class ASTree


case class NumberLiteral(token: NumToken) extends ASTree {
  def value = token.number
}


case class Name(token: IdToken) extends ASTree {
  def name = token.text
}


case class StringLiteral(token: StrToken) extends ASTree {
  def value = token.text
}


case class BinaryExpr(left: ASTree, right: ASTree, token: IdToken) extends ASTree {
  def operator = token.text
}
