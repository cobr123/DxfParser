
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{Assertions, FunSuite}

class TestUtil extends FunSuite with TimeLimitedTests {
  val timeLimit = Span(10, Seconds)


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

  def fileFromResourcesToString(file: String, encoding: String = "UTF-8") = {
    fileToString(new java.io.File("./src/test/resources/" + file).getAbsolutePath, encoding)
  }

  def fileToString(file: String, encoding: String = "UTF-8") = {
    val inStream = new java.io.FileInputStream(new java.io.File(file))
    val outStream = new java.io.ByteArrayOutputStream
    try {
      var reading = true
      while (reading) {
        inStream.read() match {
          case -1 => reading = false
          case c => outStream.write(c)
        }
      }
      outStream.flush()
    }
    finally {
      inStream.close()
    }
    new String(outStream.toByteArray(), encoding)
  }
}