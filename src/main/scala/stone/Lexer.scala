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

  def parse = source.getLines.foreach {
    line => println(parseString(line).map(x => x.get))
  }

  def parseString(string: String): List[Option[Token]] = {
    val matchers = pattern.findAllMatchIn(string)
    matchers.toList.init map {
      matcher => {
        val allMatchedString = matcher.group(0)
        if (allMatchedString.isEmpty) {
          throw new ParseException(s"bad token at line ${string}!")
        }
        matcher.subgroups match {
          case List(number, null, _:String, null, null, null) => Some(NumToken(0, number.toInt))
          case List(string, null, null, _:String, _, null) => Some(StrToken(0, string))
          case List(identifier, null, null, null, null, _: String) => Some(IdToken(0, identifier))
          case _ => None
        }
      }
    }
  }
}

class ParseException(message: String) extends RuntimeException(message)
