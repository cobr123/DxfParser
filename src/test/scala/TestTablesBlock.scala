import dxf.parser.DxfParser

class TestTablesBlock extends TestUtil {
  test("section with tables only") {
    val text =
      """  0
        |SECTION
        |  2
        |TABLES
        |  0
        |TABLE
        |  2
        |VPORT
        |  5
        |8
        |102
        |{ACAD_XDICTIONARY
        |360
        |B2
        |102
        |}
        |330
        |0
        |100
        |AcDbSymbolTable
        | 70
        |     1
        |  0
        |VPORT
        |  5
        |29
        |330
        |8
        |100
        |AcDbSymbolTableRecord
        |100
        |AcDbViewportTableRecord
        |  2
        |*Active
        | 70
        |     0
        | 10
        |0.0
        | 20
        |0.0
        | 11
        |1.0
        |  0
        |ENDTAB
        |  0
        |ENDSEC
        |  0
        |EOF
        |
        |  """

    val parser: DxfParser = new DxfParser()
//    parser.setPrintDebug(true)
    testByRule(parser, parser.dxf_main, text.stripMargin)
  }
}