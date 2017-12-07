package dxf.parser

import dxf.data._
import dxf.data.section.{DxfEntities, DxfHeader, DxfTables, DxfThumbnailImage}

import scala.util.parsing.combinator.{JavaTokenParsers, PackratParsers}

/**
  * Created by r.tabulov on 07.12.2017.
  */
class DebugDxfParser extends JavaTokenParsers with PackratParsers {
  val context = new Context()
  var print_debug = false
  var last_error: Any = None

  def setPrintDebug(printDebug: Boolean): Unit = print_debug = printDebug

  def setPrintToXml(bpPrintToXml: Boolean): Unit = context.setPrintToXml(bpPrintToXml)

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
        last_error = t
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

  lazy val variable: Parser[String] = "variable" !!! "$" ~ """[a-zA-Z0-9]+""".r ^^ {
    case v ~ n => v.toString + n.toString
  }

  def ignoreCase(str: String): Parser[String] = ("""(?i)\Q""" + str + """\E\b""").r

  lazy val keywords: Parser[Any] = (
    ZERO
      | EOF
      | ENDSEC
      | ENDTAB
      | ENDBLK
    )

  lazy val group_code: Parser[String] = "group_code" !!! not(keywords) ~> """\d+""".r //not(keywords) ~> wholeNumber

  lazy val value: Parser[String] = "value" !!! """[^{}\r\n]+""".r //"""[А-Яа-яA-Za-z0-9_\-+\.\\\*\h;:'/"=#№()]+""".r

  lazy val NL: Parser[Any] = """(\r?\n)""".r

  lazy val WS: Parser[Any] = """\h*""".r

  lazy val ZERO: Parser[Any] = ignoreCase("0")

  lazy val TWO: Parser[Any] = ignoreCase("2")

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

  /*
  https://www.autodesk.com/techpubs/autocad/acad2000/dxf/ascii_dxf_files_dxf_aa.htm
  */
  lazy val dxf_main: Parser[DxfSections] = "dxf_main" !!! (
    sections
      <~ (WS ~ ZERO ~ NL ~ EOF ~ """\s*""".r)
    ) ^^ {
    case s => new DxfSections(context, s)
  }

  lazy val sections: Parser[List[Option[Any]]] = "sections" !!! (
    opt(header_block)
      ~ opt(classes_block)
      ~ opt(tables_block)
      ~ opt(blocks_block)
      ~ opt(entities_block)
      ~ opt(objects_block)
      ~ opt(thumbnailimage_block)
    ) ^^ {
    case h ~ c ~ t ~ b ~ e ~ o ~ th => List(h, c, t, b, e, o, th)
  }

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
  lazy val header_block: Parser[DxfHeader] = "header_block" !!! (
    ((WS ~ ZERO ~ NL ~ SECTION ~ NL) ~ (WS ~ TWO ~ NL ~ HEADER ~ NL))
      ~> rep(header_variable)
      <~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfHeader(context, v)
  }

  lazy val header_variable: Parser[DxfHeaderVariable] = "header_variable" !!! (
    (WS ~> "9" ~> NL ~> variable <~ NL)
      ~ rep(group_code_and_value)
    ) ^^ {
    case n ~ v => new DxfHeaderVariable(context, n, v)
  }

  lazy val group_code_and_value: Parser[DxfGroupCodeAndValue] = "group_code_and_value" !!! (
    (WS ~> group_code <~ NL)
      ~ (WS ~> opt(dxf_value_in_cur | dxf_value) <~ NL)
    ) ^^ {
    case n ~ v => new DxfGroupCodeAndValue(context, n, v)
  }

  lazy val dxf_value: Parser[DxfValue] = "dxf_value" !!! value ^^ {
    case v => new DxfValue(context, v)
  }

  lazy val dxf_value_in_cur: Parser[DxfValue] = "dxf_value_in_cur" !!! (
    "{" ~> value <~ "}"
    ) ^^ {
    case v => new DxfValue(context, "{" + v + "}")
  }

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
      ~ (WS ~ TWO ~ NL ~ CLASSES ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ CLASS ~ NL)
        ~ (WS ~ "1" ~ NL ~ class_dxf_record ~ NL)
        ~ (WS ~ TWO ~ NL ~ class_name ~ NL)
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
  lazy val tables_block: Parser[DxfTables] = "tables_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL
      ~ WS ~ TWO ~ NL ~ TABLES ~ NL)
      ~> rep(dxf_table)
      <~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfTables(context, v)
  }

  lazy val dxf_table: Parser[DxfTable] = "dxf_table" !!! (
    ((WS ~ ZERO ~ NL ~ TABLE ~ NL)
      ~> rep(group_code_and_dict))
      ~ (rep(dxf_type_with_groups)
      <~ (WS ~ ZERO ~ NL ~ ENDTAB ~ NL))
    ) ^^ {
    case g ~ t => new DxfTable(context, g, t)
  }

  lazy val dxf_type_with_groups: Parser[DxfTypeWithGroups] = "dxf_table_type" !!! (
    (WS ~> ZERO ~> NL ~> groups_type <~ NL)
      ~ rep(group_code_and_dict)
    ) ^^ {
    case n ~ g => new DxfTypeWithGroups(context, n, g)
  }

  lazy val group_code_and_dict: Parser[DxfGroupCodeAndDict] = "group_code_and_dict" !!! (
    (WS ~> group_code <~ NL)
      ~ (WS ~> opt(dict | dxf_value) <~ NL)
    ) ^^ {
    case n ~ v => new DxfGroupCodeAndDict(context, n, v)
  }

  lazy val groups_type: Parser[String] = not(keywords) ~> """[a-zA-Z0-9_]+""".r

  lazy val handle: Parser[String] = not(keywords) ~> """[a-zA-Z0-9]+""".r

  lazy val dict_name: Parser[String] = not(keywords) ~> """[a-zA-Z0-9_]+""".r

  lazy val dict: Parser[DxfDict] = "dict" !!! (
    ("{" ~> dict_name <~ NL)
      ~ rep(group_code_and_value)
      ~ (WS ~> group_code <~ NL <~ "}")
    ) ^^ {
    case n ~ g ~ c => new DxfDict(context, n, g, c)
  }
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
      ~ (WS ~ TWO ~ NL ~ BLOCKS ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ BLOCK ~ NL)
        ~ rep(group_code_and_dict)
        ~ rep(
        (WS ~ ZERO ~ NL ~ entity_type ~ NL)
          ~ rep(group_code_and_dict)
      )
        ~ (WS ~ ZERO ~ NL ~ ENDBLK ~ NL)
        ~ rep(WS ~ group_code ~ NL ~ WS ~ opt(dict | not(AcDbBlockEnd) ~ value) ~ NL)
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
    (WS ~ ZERO ~ NL ~ SECTION ~ NL
      ~ WS ~ TWO ~ NL ~ ENTITIES ~ NL)
      ~> rep(dxf_type_with_groups)
      <~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfEntities(context, v)
  }
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
      ~ (WS ~ TWO ~ NL ~ OBJECTS ~ NL)
      ~ rep(
      (WS ~ ZERO ~ NL ~ DICTIONARY ~ NL)
        ~ rep(group_code_and_dict)
        ~ rep(
        (WS ~ ZERO ~ NL ~ object_type ~ NL)
          ~ rep(group_code_and_dict)
      )
    )
      ~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    )

  lazy val object_type: Parser[Any] = "object_type" !!! not(keywords) ~"""[a-zA-Z0-9_]+""".r

  lazy val thumbnailimage_block: Parser[Any] = "thumbnailimage_block" !!! (
    (WS ~ ZERO ~ NL ~ SECTION ~ NL
      ~ WS ~ TWO ~ NL ~ THUMBNAILIMAGE ~ NL)
      ~> rep(group_code_and_value)
      <~ (WS ~ ZERO ~ NL ~ ENDSEC ~ NL)
    ) ^^ {
    case v => new DxfThumbnailImage(context, v)
  }

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