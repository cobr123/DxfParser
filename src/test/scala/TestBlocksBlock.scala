import local.dxf.parser.DxfParser

class TestBlocksBlock extends TestUtil {
  test("section with blocks only") {
    val text =
      """  0
        |SECTION
        |  2
        |BLOCKS
        |  0
        |BLOCK
        |  5
        |20
        |330
        |1F
        |100
        |AcDbEntity
        |  8
        |0
        |100
        |AcDbBlockBegin
        |  2
        |*Model_Space
        | 70
        |     0
        | 10
        |0.0
        | 20
        |0.0
        | 30
        |0.0
        |  3
        |*Model_Space
        |  1
        |*Model_Space
        |  0
        |ENDBLK
        |  5
        |21
        |330
        |1F
        |100
        |AcDbEntity
        |  8
        |0
        |100
        |AcDbBlockEnd
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
