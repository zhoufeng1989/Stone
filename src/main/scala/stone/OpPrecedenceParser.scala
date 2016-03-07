package stone

import stone.ast.{Name, BinaryExpr, NumberLiteral, ASTree}

/**
 * Created by zhoufeng on 16/3/6.
 */
class OpPrecedenceParser (val lexer: Lexer) {
  case class Precedence (value: Int, leftAssoc: Boolean) extends Ordered[Precedence] {
    def compare(b: Precedence) = if (this.value > b.value) 1 else if (this.value == b.value) 0 else -1
  }

  val operators = Map(
    "<" -> Precedence(1, true),
    ">" -> Precedence(1, true),
    "+" -> Precedence(2, true),
    "-" -> Precedence(2, true),
    "*" -> Precedence(3, true),
    "/" -> Precedence(3, true),
    "^" -> Precedence(4, true)
  )

  def expression(): ASTree = {
    def loop(left: ASTree): ASTree = {
      getNextOpOptionPrec map {
        prec => {loop(doShift(left, prec))}
      } getOrElse left
    }
    loop(factor())
  }

  def getNextOpOptionPrec: Option[Precedence] = lexer.peek match {
    case token: IdToken => Some(operators(token.text))
    case _ => None
  }

  def doShift(left: ASTree, prec: Precedence):ASTree = {
    val token = lexer.readIdToken
    val right = factor()
    def loop(left: ASTree, prec: Precedence, right: ASTree): ASTree = {
      val nextOpOptionPrec = getNextOpOptionPrec
      nextOpOptionPrec map {
        case nextPrec if nextPrec.leftAssoc && prec < nextPrec => loop(left, prec, doShift(right, nextPrec))
        case _ => BinaryExpr(left, right, Name(token))
      } getOrElse BinaryExpr(left, right, Name(token))
    }
    loop(left, prec, right)
  }

  def factor(): ASTree = lexer.read match {
    case StrToken(_, "(") => {
      val e = expression()
      lexer.read match {
        case StrToken(_, ")") => e
        case _ => throw new ParseException("parse exception")
      }
    }
    case t: NumToken => NumberLiteral(t)
    case _ => throw new ParseException("parse exception")
  }
}
