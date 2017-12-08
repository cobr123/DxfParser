import local.dxf.parser.DxfParser

class TestClassesBlock extends TestUtil {
  test("section with classes only") {
    val text =
      """  0
        |SECTION
        |  2
        |CLASSES
        |  0
        |CLASS
        |  1
        |ACDBDICTIONARYWDFLT
        |  2
        |AcDbDictionaryWithDefault
        |  3
        |ObjectDBX Classes
        | 90
        |        0
        |280
        |     0
        |281
        |     0
        |  0
        |CLASS
        |  1
        |VISUALSTYLE
        |  2
        |AcDbVisualStyle
        |  3
        |ObjectDBX Classes
        | 90
        |     4095
        |280
        |     0
        |281
        |     0
        |  0
        |ENDSEC
        |  0
        |EOF
        |
        |  """

    val parser: DxfParser = new DxfParser()
    //parser.setPrintDebug(true)
    testByRule(parser, parser.dxf_main, text.stripMargin)
  }
}