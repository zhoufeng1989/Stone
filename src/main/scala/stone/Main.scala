package stone

/**
 * Created by zhoufeng on 16/2/21.
 */
object Main extends App {
  val filename = "expression.txt"
  val lexer = new Lexer(scala.io.Source.fromFile(filename))
  val parser = new ExprParser(lexer)
  print(parser.expression())

}
