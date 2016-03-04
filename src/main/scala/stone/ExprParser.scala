package stone

import ast._

import ast.ASTree

import scala.util.control.Breaks

/**
 * Created by zhoufeng on 16/2/25.
 */
class ExprParser(p: Lexer) {

  private val lexer = p

  def expression(): ASTree = {
    def loop(left: ASTree): ASTree = {
      lexer.peek match {
        case token: IdToken if token.text == "+" || token.text == "-" => {
          lexer.read
          val op = Name(token)
          val right = term()
          loop(BinaryExpr(left, right, op))
        }
        case _ => left
      }
    }
    loop(term())
  }

  def term(): ASTree = {
    def loop(left: ASTree): ASTree = {
      lexer.peek match {
        case token: IdToken if token.text == "*" || token.text == "/" => {
          lexer.read
          val op = Name(token)
          val right = factor()
          loop(BinaryExpr(left, right, op))
        }
        case _ => left
      }
    }
    loop(factor())
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
