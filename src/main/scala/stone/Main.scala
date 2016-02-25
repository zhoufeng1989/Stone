package stone

/**
 * Created by zhoufeng on 16/2/21.
 */
object Main extends App {
  val filename = "test.txt"
  val lexer = new Lexer(scala.io.Source.fromFile(filename))
  println(lexer.parse take 10)
}
