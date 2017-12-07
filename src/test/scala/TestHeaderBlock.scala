
class TestHeaderBlock extends TestUtil {
  test("section with header only") {
    val text =
      """  0
        |SECTION
        |  2
        |HEADER
        |  9
        |$ACADVER
        |  1
        |AC1021
        |  9
        |$DWGCODEPAGE
        |  3
        |ANSI_1251
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