package stone

/**
 * Created by zhoufeng on 16/2/21.
 */
object Main extends App {
  val filename = "expression.txt"
  val lexer1 = new Lexer(scala.io.Source.fromFile(filename))
  val op_prec_parser = new OpPrecedenceParser(lexer1)
  val lexer2 = new Lexer(scala.io.Source.fromFile(filename))
  val expr_parser = new OpPrecedenceParser(lexer2)
  println(op_prec_parser.expression())
  println(expr_parser.expression())

}
