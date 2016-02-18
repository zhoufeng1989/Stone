package stone

/**
 * Created by zhoufeng on 16/2/18.
 */
class Lexer {
  private val commentPattern = """//.*"""
  private val numberPattern = """[0-9]+"""
  private val stringPattern = """"(\"|\\|\n|[^"])*""""
  private val identityPattern = """[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\|\||\p{Punct}"""
  private val spacePattern = """\s*"""
  val pattern = s"""${spacePattern}((${commentPattern})|(${numberPattern})|(${stringPattern})|(${identityPattern}))"""
}
