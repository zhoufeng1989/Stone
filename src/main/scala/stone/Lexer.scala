package stone

/**
 * Created by zhoufeng on 16/2/18.
 */
class Lexer(val source: scala.io.Source) {
  private val commentPattern = """//.*"""
  private val numberPattern = """[0-9]+"""
  private val stringPattern = """"(\"|\\|\n|[^"])*""""
  private val identityPattern = """[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\|\||\p{Punct}"""
  private val spacePattern = """\s*"""
  val pattern = s"""${spacePattern}((${commentPattern})|(${numberPattern})|(${stringPattern})|(${identityPattern}))?""".r
  var queue = Vector[Token]()

  var stream = source.getLines.toStream.zip(Stream.from(1)).map {
    case (line, lineNumber) => parseString(lineNumber, line)
  }.flatten

  def peek(): Token = {
    val token = stream.head
    token
  }

  def read: Token = {
    val token = stream.head
    stream = stream.tail
    token
  }

  private def fillQueue(i: Int): Boolean = {
    while (queue.size < i) {
      false
    }
    false
  }

  def parseString(lineNumber:Int, string: String): List[Token] = {
    pattern.findAllMatchIn(string).toList.init.foldRight(List(EOL(lineNumber)):List[Token]) {
      (matcher, z) => {
        val allMatchedString = matcher.group(0)
        if (allMatchedString.isEmpty) {
          throw new ParseException(s"bad token at line ${lineNumber}!")
        }
        matcher.subgroups match {
          case List(number, null, _:String, null, null, null) => NumToken(lineNumber, number.toInt)::z
          case List(string, null, null, _:String, _, null) => StrToken(lineNumber, string)::z
          case List(identifier, null, null, null, null, _: String) => IdToken(lineNumber, identifier)::z
          case _ => z
        }
      }
    }
  }
}

class ParseException(message: String) extends RuntimeException(message)
