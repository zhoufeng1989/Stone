package stone

/**
 * Created by zhoufeng on 16/2/15.
 */
abstract class Token {
  def isIdentifier = false
  def isNumber = false
  def isString = false
  def number: Number = {throw new StoneException("not number token")}
  def text = ""
  def lineNumber: Int
}


case object EOF extends Token {
  val lineNumber = -1
}


case class EOL(val lineNumber: Int) extends Token {
  override def isString = true
  override val text = "\n"
}


case class NumToken(val lineNumber: Int, override val number: Number) extends Token {
  override def isNumber = true
  override def text = number.toString
}


case class IdToken(val lineNumber: Int, override val text: String) extends Token {
  override def isIdentifier = true
}


case class StrToken(val lineNumber: Int, override val text: String) extends Token {
  override def isString = true
}


class StoneException(message: String) extends RuntimeException(message)
