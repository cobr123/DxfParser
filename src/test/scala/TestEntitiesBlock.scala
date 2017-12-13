import local.dxf.parser.DxfParser

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
  test("section with entities only to xml") {
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
    val xml =
      """<SECTIONS>
        |<SECTION name='ENTITIES' code='2' line='1'>
        |<TYPES>
        |<TYPE code='0' name='INSERT' line='5'>
        |<GROUPS>
        |<GROUP code='5' line='7'>
        |<VALUE line='8'>71</VALUE>
        |</GROUP>
        |<GROUP code='330' line='9'>
        |<VALUE line='10'>1F</VALUE>
        |</GROUP>
        |<GROUP code='100' line='11'>
        |<VALUE line='12'>AcDbEntity</VALUE>
        |</GROUP>
        |<GROUP code='8' line='13'>
        |<VALUE line='14'>0</VALUE>
        |</GROUP>
        |<GROUP code='100' line='15'>
        |<VALUE line='16'>AcDbBlockReference</VALUE>
        |</GROUP>
        |<GROUP code='2' line='17'>
        |<VALUE line='18'>U0</VALUE>
        |</GROUP>
        |<GROUP code='10' line='19'>
        |<VALUE line='20'>0.0</VALUE>
        |</GROUP>
        |<GROUP code='20' line='21'>
        |<VALUE line='22'>0.0</VALUE>
        |</GROUP>
        |<GROUP code='30' line='23'>
        |<VALUE line='24'>0.0</VALUE>
        |</GROUP>
        |</GROUPS>
        |</TYPE>
        |</TYPES>
        |</SECTION>
        |</SECTIONS>"""

    val parser: DxfParser = new DxfParser()
    parser.setPrintToXml(true)
    //parser.setPrintDebug(true)
    testByRuleXml(parser, parser.dxf_main, text.stripMargin, xml.stripMargin)
  }
}
