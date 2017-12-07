
class TestEntitiesBlock extends TestUtil {
  test("section with entities only") {
    val text =
      """  0
        |SECTION
        |  2
        |ENTITIES
        |  0
        |INSERT
        |  5
        |71
        |330
        |1F
        |100
        |AcDbEntity
        |  8
        |0
        |100
        |AcDbBlockReference
        |  2
        |U0
        | 10
        |0.0
        | 20
        |0.0
        | 30
        |0.0
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
