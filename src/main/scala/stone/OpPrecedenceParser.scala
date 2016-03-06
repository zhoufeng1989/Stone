package stone

import stone.ast.{NumberLiteral, ASTree}

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
    "^" -> Precedence(3, true)
  )

  def expression(): ASTree = {
    def loop(left: ASTree, result: ASTree) = {
      val idOption = lexer.peek match {
        case token: IdToken => Some(operators(token.text))
        case _ => None
      }
      idOption.map
    }
  }

  def getNextOpOption: Option[Precedence] = lexer.peek match {
    case token: IdToken => Some(operators(token.text))
    case _ => None
  }

  def doShift(left: ASTree, prec: Precedence) = {
    def loop(left: ASTree, prec: Int, result: ASTree) = {
      val token = lexer.read
      val factor = factor()
      val opOption = getNextOpOption
      opOption map {
        case prec
      }
    }

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
