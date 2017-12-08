import local.dxf.parser.DxfParser
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{Assertions, FunSuite}

class TestUtil extends FunSuite with TimeLimitedTests {
  val timeLimit = Span(10, Seconds)


//  def testByRule(parser: DxfParser, rule: Any, text: String): Unit = {
//    val res = parser.parseByRule(rule.asInstanceOf[parser.Parser[Any]], text)
//    res match {
//      case (0, tuples) => println(tuples)
//      case (_, err) => {
//        println(err)
//        Assertions.fail()
//      }
//    }
//    ()
//  }
  def testByRule(parser: DxfParser, rule: Any, text: String, textForCompareWithSpace: String = "", showResult: Boolean = false, firstTry: Boolean = true, ignoreCase: Boolean = true): Unit = {
    val res = parser.parseByRule(rule.asInstanceOf[parser.Parser[Any]], text)

    if (res._1 == 0) {
      val resText = res._2.toString
      if (compareWoSpaceAndNewLines(text, resText)) {
        if (firstTry) {
          testByRule(parser, rule, resText, textForCompareWithSpace, showResult, firstTry = false, ignoreCase)
        } else if (!textForCompareWithSpace.isEmpty) {
          if (compareWoSpaces(resText, textForCompareWithSpace, ignoreCase)) {
            if (showResult) {
              println(resText)
            }
          } else {
            println("=================after==================")
            println(resText)
            println("=================expected===============")
            println(textForCompareWithSpace)
            println("========================================")
            Assertions.fail()
          }
        } else if (showResult) {
          println(resText)
        }
      } else {
        println("=================after==================")
        println(resText)
        println("=================before=================")
        println(text)
        println("========================================")
        Assertions.fail()
      }
    }
    else {
      println(parser.last_error.toString)
      info("parser failed")
      Assertions.fail()
    }
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

  def compareWoSpaceAndNewLines(oldStr: String, newStr: String, ignoreCase: Boolean = true): Boolean = {
    val oldDataTextWoSpaces: String = oldStr.replaceAll("\\s", "").replaceAll("\\n", "")
    val newDataTextWoSpaces: String = newStr.replaceAll("\\s", "").replaceAll("\\n", "")
    if (ignoreCase) {
      newDataTextWoSpaces.equalsIgnoreCase(oldDataTextWoSpaces)
    } else {
      newDataTextWoSpaces.equals(oldDataTextWoSpaces)
    }
  }
  def compareWoSpaces(oldStr: String, newStr: String, ignoreCase: Boolean = true): Boolean = {
    val oldDataTextWoSpaces: String = oldStr.replaceAll("\\s", "")
    val newDataTextWoSpaces: String = newStr.replaceAll("\\s", "")
    if (ignoreCase) {
      newDataTextWoSpaces.equalsIgnoreCase(oldDataTextWoSpaces)
    } else {
      newDataTextWoSpaces.equals(oldDataTextWoSpaces)
    }
  }

  def compareWithSpaces(oldStr: String, newStr: String, ignoreCase: Boolean = true): Boolean = {
    val oldDataTextWoSpaces: String = oldStr.replaceAll("\\r", "")
    val newDataTextWoSpaces: String = newStr.replaceAll("\\r", "")
    if (ignoreCase) {
      newDataTextWoSpaces.equalsIgnoreCase(oldDataTextWoSpaces)
    } else {
      newDataTextWoSpaces.equals(oldDataTextWoSpaces)
    }
  }

  def convertByRule(parser: DxfParser, rule: Any, text: String, textForCompareWithSpace: String = "", showResult: Boolean = false): Unit = {
    val res = parser.parseByRule(rule.asInstanceOf[parser.Parser[Any]], text)

    if (res._1 == 0) {
      val resText = res._2.toString
      testByRule(parser, rule, resText, textForCompareWithSpace, showResult)
    } else {
      println(parser.last_error.toString)
      info("parser failed")
      Assertions.fail()
    }
  }
}