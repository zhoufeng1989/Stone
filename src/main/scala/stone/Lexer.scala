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

  def parse = source.getLines.toList.zip(Stream.from(1)).foreach {
    case (line, lineNumber) => println(parseString(lineNumber, line))
  }

  def parseString(lineNumber:Int, string: String): List[Token] = {
    pattern.findAllMatchIn(string).toList.init.foldLeft(List(EOL(lineNumber)):List[Token]) {
      (z, matcher) => {
        val allMatchedString = matcher.group(0)
        if (allMatchedString.isEmpty) {
          throw new ParseException(s"bad token at line ${lineNumber}!")
        }
        matcher.subgroups match {
          case List(number, null, _:String, null, null, null) => NumToken(0, number.toInt)::z
          case List(string, null, null, _:String, _, null) => StrToken(0, string)::z
          case List(identifier, null, null, null, null, _: String) => IdToken(0, identifier)::z
          case _ => z
        }
      }
    }
  }
}

class ParseException(message: String) extends RuntimeException(message)
