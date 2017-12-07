

import scala.util.parsing.combinator.{JavaTokenParsers, PackratParsers}

/**
  * Created by r.tabulov on 07.12.2017.
  */
class DebugDxfParser extends JavaTokenParsers with PackratParsers {
  var print_debug = false

  def setPrintDebug(printDebug: Boolean) = print_debug = printDebug

  final class Wrap[+T](name: String, parser: Parser[T]) extends Parser[T] {
    def log(msg: String): Unit = {
      println(msg)
    }

    def apply(in: Input): ParseResult[T] = {
      if (print_debug) {
        log("Entering parser " + name + ":")
        //println(Util.getPad + "Reading " + first)
      }

      val t = parser.apply(in)

      if (t.successful) {
        if (print_debug) {
          log(name + " succeeded")
          log("" + t)
        }
      } else {
        if (print_debug) {
          log(name + " failed")
        }
      }
      t
    }
  }

  implicit def toWrapped(name: String) = new {
    def !!![T](p: Parser[T]) = {
      if (print_debug) new Wrap(name, p)
      else p
    }

    //lazy val !!![T](p: Parser[T]) = new Wrap(name, p) //for debugging
    //    lazy val !!![T](p:Parser[T]) = p              //for production
  }
}

class DxfParser extends DebugDxfParser {
  override def skipWhitespace = false

  lazy val variable: Parser[Any] = "variable" !!! "$" ~ """[a-zA-Z0-9]+""".r

  def ignoreCase(str: String): Parser[String] = ("""(?i)\Q""" + str + """\E\b""").r

  lazy val keywords: Parser[Any] = (
    ZERO
      | EOF
      | ENDSEC
      | ENDTAB
      | ENDBLK
    )

  lazy val group_code: Parser[Any] = "group_code" !!! not(keywords) ~ """\d+""".r //not(keywords) ~> wholeNumber

  lazy val value: Parser[Any] = "value" !!! """[^{}\r\n]+""".r //"""[А-Яа-яA-Za-z0-9_\-+\.\\\*\h;:'/"=#№()]+""".r

  lazy val NL: Parser[Any] = """(\r?\n)""".r

  lazy val WS: Parser[Any] = """\h*""".r

  lazy val ZERO: Parser[Any] = ignoreCase("0")

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

  lazy val EOF: Parser[Any] = ignoreCase("EOF")

  lazy val AcDbBlockEnd: Parser[Any] = ignoreCase("AcDbBlockEnd")

  /*
  https://www.autodesk.com/techpubs/autocad/acad2000/dxf/ascii_dxf_files_dxf_aa.htm
  */
  lazy val dxf_main: Parser[Any] = "dxf_main" !!! (
    sections
      ~ (WS ~ ZERO ~ NL ~ EOF ~ """\s*""".r)
    )

  lazy val sections: Parser[Any] = "sections" !!! (
    opt(header_block)
      ~ opt(classes_block)
      ~ opt(tables_block)
      ~ opt(blocks_block)
      ~ opt(entities_block)
      ~ opt(objects_block)
    )

  /*Applications can retrieve the values of these variables with the AutoLISP getvar function.

  The following is an example of the HEADER section of a DXF file:
    0
  SECTION
    2
  HEADER

  Beginning of HEADER section
    9
  $<variable>
  <group code>
  <value>
  Repeats for each header variable

    0
  ENDSEC
  End of HEADER section
  */
  lazy val header_block: Parser[Any] = "header_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL)
      ~ (WS ~ "2" ~ NL ~ HEADER ~ NL)
      ~ rep((WS ~ "9" ~ NL ~ variable ~ NL) ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(("{" ~ value ~ "}") | value) ~ NL))
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )

  /*The following is an example of the CLASSES section of a DXF file:
    0
  SECTION
    2
  CLASSES

  Beginning of CLASSES section
    0
  CLASS
    1
  <class dxf record>
    2
  <class name>
    3
  <app name>
  90
  <flag>
  280
  <flag>
  281
  <flag>
  Repeats for each entry

    0
  ENDSEC
  End of CLASSES section
  */
  lazy val classes_block: Parser[Any] = "classes_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL)
      ~ (WS ~ "2" ~ NL ~ CLASSES ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ CLASS ~ NL)
        ~ (WS ~ "1" ~ NL ~ class_dxf_record ~ NL)
        ~ (WS ~ "2" ~ NL ~ class_name ~ NL)
        ~ (WS ~ "3" ~ NL ~ app_name ~ NL)
        ~ rep(WS ~ wholeNumber ~ NL ~ WS ~ wholeNumber ~ NL)
    )
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )

  lazy val class_dxf_record: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9]+""".r

  lazy val class_name: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9]+""".r

  lazy val app_name: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9_\h]+""".r

  /*The following is an example of the TABLES section of a DXF file.
    0
  SECTION
    2
  TABLES

  Beginning of TABLES section
    0
  TABLE
    2
  <table type>
    5
  <handle>
  100
  AcDbSymbolTable
  70
  <max. entries>
  Common table group codes,
  repeats for each entry

    0
  <table type>
    5
  <handle>
  100
  AcDbSymbolTableRecord
  .
  . <data>
  .
  Table entry data, repeats,
  for each table record

    0
  ENDTAB
  End of table

    0
  ENDSEC
  End of TABLES section
  */
  lazy val tables_block: Parser[Any] = "tables_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL)
      ~ (WS ~ "2" ~ NL ~ TABLES ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ TABLE ~ NL)
        ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
        ~ rep(
        (WS ~ ZERO ~ NL ~ table_type ~ NL)
          ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
      )
        ~ (WS ~ ZERO ~ NL ~ ENDTAB ~ NL)
    )
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )

  lazy val table_type: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9_]+""".r

  lazy val handle: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9]+""".r

  lazy val dic_name: Parser[Any] = not(keywords) ~"""[a-zA-Z0-9_]+""".r

  lazy val dic: Parser[Any] = "dic" !!! (
    "{" ~ dic_name ~ NL
      ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(value) ~ NL)
      ~ WS ~ group_code ~ NL
      ~ "}"
    )
  /*The following is an example of the BLOCKS section of a DXF file:
    0
  SECTION
    2
  BLOCKS

  Beginning of BLOCKS section
    0
  BLOCK
    5
  <handle>
  100
  AcDbEntity
    8
  <layer>
  100
  AcDbBlockBegin
    2
  <block name>
  70
  <flag>
  10
  <X value>
  20
  <Y value>
  30
  <Z value>
    3
  <block name>
    1
  <xref path>

  Begins each block entry
  (a block entity definition)

    0
  <entity type>
  .
  . <data>
  .

  One entry for each entity definition within the block

    0
  ENDBLK
    5
  <handle>
  100
  AcDbBlockEnd
  End of each block entry
  (an endblk entity definition)

    0
  ENDSEC
  End of BLOCKS section
  * */
  lazy val blocks_block: Parser[Any] = "blocks_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL)
      ~ (WS ~ "2" ~ NL ~ BLOCKS ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ BLOCK ~ NL)
        ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
        ~ rep(
        (WS ~ ZERO ~ NL ~ entity_type ~ NL)
          ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
      )
        ~ (WS ~ ZERO ~ NL ~ ENDBLK ~ NL)
        ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | not(AcDbBlockEnd) ~ value) ~ NL)
        ~ (WS ~ "100" ~ NL ~ AcDbBlockEnd ~ NL)
    )
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )

  lazy val entity_type: Parser[Any] = "entity_type" !!! not(keywords) ~"""[a-zA-Z0-9_]+""".r

  /*The following is an example of the ENTITIES section of a DXF file:
    0
  SECTION
    2
  ENTITIES

  Beginning of ENTITIES section
    0
  <entity type>
    5
  <handle>
  330
  <pointer to owner>
  100
  AcDbEntity
    8
  <layer>
  100
  AcDb<classname>
  .
  . <data>
  .
  One entry for each entity definition

    0
  ENDSEC
  End of ENTITIES section
  * */
  lazy val entities_block: Parser[Any] = "entities_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL)
      ~ (WS ~ "2" ~ NL ~ ENTITIES ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ entity_type ~ NL)
        ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
    )
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )
  /*The following is an example of the OBJECTS section of a DXF file:
    0
  SECTION
    2
  OBJECTS

  Beginning of OBJECTS section
    0
  DICTIONARY
    5
  <handle>
  100
  AcDbDictionary

  Beginning of named object
  dictionary (root dictionary
  object)

    3
  <dictionary name>
  350
  <handle of child>

  Repeats for each entry
    0
  <object type>
  .
  . <data>
  .
  Groups of object data

    0
  ENDSEC
  End of OBJECTS section
  * */
  lazy val objects_block: Parser[Any] = "objects_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL)
      ~ (WS ~ "2" ~ NL ~ OBJECTS ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ DICTIONARY ~ NL)
        ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
        ~ rep(
        (WS ~ ZERO ~ NL ~ object_type ~ NL)
          ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dic | value) ~ NL)
      )
    )
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )

  lazy val object_type: Parser[Any] = "object_type" !!! not(keywords) ~"""[a-zA-Z0-9_]+""".r


  def parseByRule(rule: Parser[Any], text: String) = {
    parseAll(rule,
      text) match {
      case Success(lup, _) => (0, lup.toString)
      case x => (-1, x.toString)
    }
  }

}

object TestDxfParser extends DxfParser with App {
  val source = scala.io.Source.fromFile("d:\\test.dxf", "windows-1251")
  val lines: String = source.mkString
  source.close()

  parseAll(dxf_main,
    lines) match {
    case Success(lup, _) => println(lup)
    case x => println(x)
  }
}