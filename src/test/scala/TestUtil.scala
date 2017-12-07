
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{Assertions, FunSuite}

class TestUtil extends FunSuite with TimeLimitedTests {
  val timeLimit = Span(1, Seconds)


  def testByRule(parser: DxfParser, rule: Any, text: String): Unit = {
    val res = parser.parseByRule(rule.asInstanceOf[parser.Parser[Any]], text)
    res match {
      case (0, tuples) => println(tuples)
      case (_, err) => {
        println(err)
        Assertions.fail()
      }
    }
    ()
  }
}