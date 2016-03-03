package stone

import ast._

import ast.ASTree
/**
 * Created by zhoufeng on 16/2/25.
 */
class ExprParser(p: Lexer) {

  private val lexer = p

  def expression(): ASTree = {
    val left = term()
    lexer.peek match {
      case token: IdToken if token.text == "+" || token.text == "-" => {
        lexer.read
        val op = Name(token)
        val right = expression()
        BinaryExpr(left, right, op)
      }
      case _ => left
    }
  }

  def term(): ASTree = {
    val left = factor()
    lexer.peek match {
      case token: IdToken if token.text == "*" || token.text == "/" => {
        lexer.read
        val op = Name(token)
        val right = term()
        BinaryExpr(left, right, op)
      }
      case _ => left
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

  private def isToken(name: String): Boolean = {
    val token = lexer.peek
    return token.isIdentifier && token.text == name
  }

  private def readToken(name: String): Token = {
    if (isToken(name)) {
      lexer.read
    }
    else {
      throw new ParseException("parse exception")
    }
  }
}


