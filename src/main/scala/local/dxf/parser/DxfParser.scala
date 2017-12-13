package local.dxf.parser

import local.dxf.data._
import local.dxf.data.section._

import scala.language.reflectiveCalls
import scala.language.implicitConversions
import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by r.tabulov on 07.12.2017.
  */
class DebugDxfParser extends JavaTokenParsers {
  val context = new Context()
  var print_debug = false
  var count_rule_use_statistics = false
  val rule_use_statistics = scala.collection.mutable.HashMap.empty[String, scala.collection.mutable.HashMap[String, Long]]
  var lastRuleStatPrint: Long = System.currentTimeMillis
  var last_error: Any = None

  def setPrintDebug(printDebug: Boolean): Unit = print_debug = printDebug

  def setPrintToXml(bpPrintToXml: Boolean): Unit = context.setPrintToXml(bpPrintToXml)

  def setCountRuleUseStatistics(countRuleUseStatistics: Boolean): Unit = count_rule_use_statistics = countRuleUseStatistics

  final class Wrap[+T](name: String, parser: Parser[T]) extends Parser[T] {
    def log(msg: String): Unit = {
      println(msg)
    }

    def apply(in: Input): ParseResult[T] = {
      if (print_debug) {
        log("Entering parser " + name + ":")
        //println(Util.getPad + "Reading " + first)
      }

      //context.indent()
      var start = 0L
      var diff = 0L
      if (count_rule_use_statistics) {
        start = System.currentTimeMillis
        if (System.currentTimeMillis - lastRuleStatPrint > 30000) {
          Util.printStatistics(rule_use_statistics)
          lastRuleStatPrint = System.currentTimeMillis
        }
      }
      val t = parser.apply(in)
      if (count_rule_use_statistics) {
        diff = System.currentTimeMillis - start
      }

      //if (print_debug) {
      //  println(context.getPad + name + ".apply for token " + in.first + " at position " + in.pos + " offset " + in.offset + " returns " + t)
      //}
      //context.unindent()

      var stat = rule_use_statistics.get(name)
      if (count_rule_use_statistics) {
        if (stat.isEmpty) {
          stat = Option(scala.collection.mutable.HashMap("successful" -> 0, "error" -> 0, "spend" -> 0))
          rule_use_statistics.put(name, stat.get)
        }
      }

      if (t.successful) {
        if (print_debug) {
          log(name + " succeeded")
          log("" + t)
        }
        if (count_rule_use_statistics) {
          val cnt = stat.get.get("successful").get
          stat.get.put("successful", cnt + 1)
        }
      } else {
        if (print_debug) {
          log(name + " failed")
        }
        if (count_rule_use_statistics) {
          val cnt = stat.get.get("error").get
          stat.get.put("error", cnt + 1)
        }
        last_error = t
      }
      if (count_rule_use_statistics) {
        val spend = stat.get.get("spend").get
        stat.get.put("spend", spend + diff)
      }
      t
    }
  }

  implicit def toWrapped(name: String) = new {
    def !!![T](p: Parser[T]) = {
      if (print_debug || count_rule_use_statistics) new Wrap(name, p)
      else p
    }

    //lazy val !!![T](p: Parser[T]) = new Wrap(name, p) //for debugging
    //    lazy val !!![T](p:Parser[T]) = p              //for production
  }
}

class DxfParser extends DebugDxfParser {
  override def skipWhitespace = false

  lazy val variable: Parser[String] = "variable" !!! "$" ~ """[a-zA-Z0-9]+""".r ^^ {
    case v ~ n => v.toString + n.toString
  }

  def ignoreCase(str: String): Parser[String] = ("""(?i)\Q""" + str + """\E\b""").r

  lazy val keywords: Parser[Any] = (
    ZERO
      | NINE
      | DICTIONARY
      | EOF
      | ENDSEC
      | ENDTAB
      | ENDBLK
    )

  lazy val group_code: Parser[String] = "group_code" !!! not(keywords) ~> """\d+""".r //not(keywords) ~> wholeNumber

  lazy val value: Parser[String] = "value" !!! """[^{}\r\n]*""".r //"""[А-Яа-яA-Za-z0-9_\-+\.\\\*\h;:'/"=#№()]+""".r

  lazy val NL: Parser[Any] = """(\r?\n)""".r

  lazy val ZERO: Parser[Any] = ignoreCase("0")

  lazy val TWO: Parser[Any] = ignoreCase("2")

  lazy val NINE: Parser[Any] = ignoreCase("9")

  lazy val SECTION: Parser[Any] = ignoreCase("SECTION")

  lazy val ENDSEC: Parser[Any] = ignoreCase("ENDSEC")

  lazy val HEADER: Parser[Any] = ignoreCase("HEADER")

  lazy val CLASSES: Parser[Any] = ignoreCase("CLASSES")

  lazy val CLASS: Parser[Any] = ignoreCase("CLASS")

  lazy val TABLES: Parser[Any] = ignoreCase("TABLES")

  lazy val TABLE: Parser[Any] = ignoreCase("TABLE")

  lazy val ENDTAB: Parser[Any] = ignoreCase("ENDTAB")

  lazy val BLOCKS: Parser[Any] = ignoreCase("BLOCKS")

  lazy val BLOCK: Parser[Any] = ignoreCase("BLOCK")

  lazy val ENDBLK: Parser[Any] = ignoreCase("ENDBLK")

  lazy val ENTITIES: Parser[Any] = ignoreCase("ENTITIES")

  lazy val OBJECTS: Parser[Any] = ignoreCase("OBJECTS")

  lazy val DICTIONARY: Parser[Any] = ignoreCase("DICTIONARY")

  lazy val THUMBNAILIMAGE: Parser[Any] = ignoreCase("THUMBNAILIMAGE")

  lazy val EOF: Parser[Any] = ignoreCase("EOF")

  lazy val AcDbBlockEnd: Parser[Any] = ignoreCase("AcDbBlockEnd")

  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/ascii_dxf_files_dxf_aa.htm
  lazy val dxf_main: Parser[DxfSections] = "dxf_main" !!! (
    sections
      <~ (ZERO ~ NL ~ EOF ~ """\s*""".r)
    ) ^^ {
    case s => new DxfSections(context, s)
  }

  lazy val sections: Parser[List[Option[DxfSection]]] = "sections" !!! (
    opt(positioned(header_block))
      ~ opt(positioned(classes_block))
      ~ opt(positioned(tables_block))
      ~ opt(positioned(blocks_block))
      ~ opt(positioned(entities_block))
      ~ opt(positioned(objects_block))
      ~ opt(positioned(thumbnailimage_block))
    ) ^^ {
    case h ~ c ~ t ~ b ~ e ~ o ~ th => List(h, c, t, b, e, o, th)
  }

  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/header_group_codes_in_dxf_files_dxf_aa.htm
  lazy val header_block: Parser[DxfHeader] = "header_block" !!! (
    ((ZERO ~ NL ~ SECTION ~ NL) ~ (TWO ~ NL ~ HEADER ~ NL))
      ~> rep(header_variable)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfHeader(context, v)
  }

  lazy val header_variable: Parser[DxfHeaderVariable] = positioned(header_variable_positioned)
  lazy val header_variable_positioned: Parser[DxfHeaderVariable] = "header_variable" !!! (
    (NINE ~> NL ~> variable <~ NL)
      ~ rep(group_code_and_value)
    ) ^^ {
    case n ~ v => new DxfHeaderVariable(context, n, v)
  }

  lazy val group_code_and_value: Parser[DxfGroupCodeAndValue] = positioned(group_code_and_value_positioned)
  lazy val group_code_and_value_positioned: Parser[DxfGroupCodeAndValue] = "group_code_and_value" !!! (
    (group_code <~ NL)
      ~ ((dxf_value_in_cur | dxf_value) <~ NL)
    ) ^^ {
    case n ~ v => new DxfGroupCodeAndValue(context, n, v)
  }

  lazy val dxf_value: Parser[DxfValue] = positioned(dxf_value_positioned)
  lazy val dxf_value_positioned: Parser[DxfValue] = "dxf_value" !!! value ^^ {
    case v => new DxfValue(context, v)
  }

  lazy val dxf_value_in_cur: Parser[DxfValue] = positioned(dxf_value_in_cur_positioned)
  lazy val dxf_value_in_cur_positioned: Parser[DxfValue] = "dxf_value_in_cur" !!! (
    "{" ~> value <~ "}"
    ) ^^ {
    case v => new DxfValue(context, "{" + v + "}")
  }

  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/class_group_codes_in_dxf_files_dxf_aa.htm
  lazy val classes_block: Parser[DxfClasses] = "classes_block" !!! (
    (ZERO ~ NL ~ SECTION ~ NL
      ~ TWO ~ NL ~ CLASSES ~ NL)
      ~> rep(dxf_class)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfClasses(context, v)
  }

  lazy val dxf_class: Parser[DxfClass] = positioned(dxf_class_positioned)
  lazy val dxf_class_positioned: Parser[DxfClass] = "dxf_class" !!! (
    (ZERO ~ NL ~ CLASS ~ NL)
      ~> rep(group_code_and_dict)
    ) ^^ {
    case v => new DxfClass(context, v)
  }

  //  lazy val class_dxf_record: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9]+""".r

  //  lazy val class_name: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9]+""".r

  //  lazy val app_name: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9_\h]+""".r

  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/symbol_table_group_codes_in_dxf_files_dxf_aa.htm
  lazy val tables_block: Parser[DxfTables] = "tables_block" !!! (
    (ZERO ~ NL ~ SECTION ~ NL
      ~ TWO ~ NL ~ TABLES ~ NL)
      ~> rep(dxf_table)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfTables(context, v)
  }

  lazy val dxf_table: Parser[DxfTable] = positioned(dxf_table_positioned)
  lazy val dxf_table_positioned: Parser[DxfTable] = "dxf_table" !!! (
    ((ZERO ~ NL ~ TABLE ~ NL)
      ~> rep(group_code_and_dict))
      ~ (rep(dxf_type_with_groups)
      <~ (ZERO ~ NL ~ ENDTAB ~ NL))
    ) ^^ {
    case g ~ t => new DxfTable(context, g, t)
  }

  lazy val dxf_type_with_groups: Parser[DxfTypeWithGroups] = positioned(dxf_type_with_groups_positioned)
  lazy val dxf_type_with_groups_positioned: Parser[DxfTypeWithGroups] = "dxf_table_type" !!! (
    (ZERO ~> NL ~> groups_type <~ NL)
      ~ rep(group_code_and_dict)
    ) ^^ {
    case n ~ g => new DxfTypeWithGroups(context, n, g)
  }

  lazy val group_code_and_dict: Parser[DxfGroupCodeAndDict] = positioned(group_code_and_dict_positioned)
  lazy val group_code_and_dict_positioned: Parser[DxfGroupCodeAndDict] = "group_code_and_dict" !!! (
    (group_code <~ NL)
      ~ ((dict | dxf_value) <~ NL)
    ) ^^ {
    case n ~ v => new DxfGroupCodeAndDict(context, n, v)
  }

  lazy val groups_type: Parser[String] = not(keywords) ~> """[a-zA-Z0-9_]+""".r

  //  lazy val handle: Parser[String] = not(keywords) ~> """[a-zA-Z0-9]+""".r

  lazy val dict_name: Parser[String] = not(keywords) ~> """[a-zA-Z0-9_]+""".r

  lazy val dict: Parser[DxfDict] = positioned(dict_positioned)
  lazy val dict_positioned: Parser[DxfDict] = "dict" !!! (
    ("{" ~> dict_name <~ NL)
      ~ rep(group_code_and_value)
      ~ (group_code <~ NL <~ "}")
    ) ^^ {
    case n ~ g ~ c => new DxfDict(context, n, g, c)
  }
  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/blocks_group_codes_in_dxf_files_dxf_aa.htm
  lazy val blocks_block: Parser[DxfBlocks] = "blocks_block" !!! (
    (ZERO ~ NL ~ SECTION ~ NL
      ~ TWO ~ NL ~ BLOCKS ~ NL)
      ~> rep(dxf_block)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfBlocks(context, v)
  }

  lazy val dxf_block: Parser[DxfBlock] = positioned(dxf_block_positioned)
  lazy val dxf_block_positioned: Parser[DxfBlock] = "dxf_block" !!! (
    ((ZERO ~ NL ~ BLOCK ~ NL)
      ~> rep(group_code_and_dict))
      ~ rep(dxf_type_with_groups)
      ~ ((ZERO ~ NL ~ ENDBLK ~ NL)
      ~> rep(group_code_and_dict))
    ) ^^ {
    case g ~ t ~ g2 => new DxfBlock(context, g, t, g2)
  }


  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/entity_group_codes_in_dxf_files_dxf_aa.htm
  lazy val entities_block: Parser[DxfEntities] = "entities_block" !!! (
    (ZERO ~ NL ~ SECTION ~ NL
      ~ TWO ~ NL ~ ENTITIES ~ NL)
      ~> rep(dxf_type_with_groups)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfEntities(context, v)
  }
  //https://www.autodesk.com/techpubs/autocad/acad2000/dxf/object_group_codes_in_dxf_files_dxf_aa.htm
  lazy val objects_block: Parser[DxfObjects] = "objects_block" !!! (
    (ZERO ~ NL ~ SECTION ~ NL
      ~ TWO ~ NL ~ OBJECTS ~ NL)
      ~> rep(object_dictionary)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfObjects(context, v)
  }

  lazy val object_dictionary: Parser[DxfObjectDictionary] = positioned(object_dictionary_positioned)
  lazy val object_dictionary_positioned: Parser[DxfObjectDictionary] = "object_dictionary" !!! (
    ((ZERO ~ NL ~ DICTIONARY ~ NL)
      ~> rep(group_code_and_dict))
      ~ rep(dxf_type_with_groups)
    ) ^^ {
    case g ~ t => new DxfObjectDictionary(context, g, t)
  }

  lazy val thumbnailimage_block: Parser[DxfThumbnailImage] = "thumbnailimage_block" !!! (
    (ZERO ~ NL ~ SECTION ~ NL
      ~ TWO ~ NL ~ THUMBNAILIMAGE ~ NL)
      ~> rep(group_code_and_value)
      <~ (ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfThumbnailImage(context, v)
  }

  def preProcessText(text: String): String = {
    val arr = text.split("\\r?\\n")
    var idx = 0
    for (line <- arr) {
      arr(idx) = line.replaceAll("^[ \\t]+", "").replaceAll("[ \\t]+$", "")
      idx = idx + 1
    }
    arr.mkString("\n")
  }

  def parseByRule(rule: Parser[Any], text: String): (Int, String) = {
    parseAll(rule,
      preProcessText(text)) match {
      case Success(lup: ToDxfXml, _) => {
        val sb = new StringBuilder
        lup.toDxf(sb)
        (0, sb.toString)
      }
      case x => (-1, x.toString)
    }
  }

  def parseByRuleXml(rule: Parser[Any], text: String): (Int, String) = {
    parseAll(rule,
      preProcessText(text)) match {
      case Success(lup: ToDxfXml, _) => {
        val sb = new StringBuilder
        lup.toXml(sb)
        (0, sb.toString)
      }
      case x => (-1, x.toString)
    }
  }

  def convertToXml(text: String): Array[Any] = {
    setPrintToXml(true)
    parseAll(dxf_main, preProcessText(text)) match {
      case Success(lup, _) => {
        val sb = new StringBuilder
        lup.toXml(sb)
        Array(0, sb.toString)
      }
      case x => Array(-1, x.toString)
    }
  }
}

object TestDxfParser extends DxfParser with App {
  val source = scala.io.Source.fromFile("d:\\test.dxf", "windows-1251")
  val lines: String = source.mkString
  source.close()

  parseAll(dxf_main,
    preProcessText(lines)) match {
    case Success(lup, _) => println(lup)
    case x => println(x)
  }
}