import local.dxf.parser.DxfParser

class TestBlocksBlock extends TestUtil {
  test("section with blocks only") {
    val text =
      """    0
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
  test("section with blocks only to xml") {
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
    val xml =
      """<SECTIONS>
        |  <SECTION name='BLOCKS' code='2' line='1'>
        |  <BLOCKS>
        |  <BLOCK code='0' line='5'>
        |    <GROUPS>
        |    <GROUP code='5' line='7'>
        |    <VALUE line='8'>20</VALUE>
        |    </GROUP>
        |    <GROUP code='330' line='9'>
        |    <VALUE line='10'>1F</VALUE>
        |    </GROUP>
        |    <GROUP code='100' line='11'>
        |    <VALUE line='12'>AcDbEntity</VALUE>
        |    </GROUP>
        |    <GROUP code='8' line='13'>
        |    <VALUE line='14'>0</VALUE>
        |    </GROUP>
        |    <GROUP code='100' line='15'>
        |    <VALUE line='16'>AcDbBlockBegin</VALUE>
        |    </GROUP>
        |    <GROUP code='2' line='17'>
        |    <VALUE line='18'>*Model_Space</VALUE>
        |    </GROUP>
        |    <GROUP code='70' line='19'>
        |    <VALUE line='20'>0</VALUE>
        |    </GROUP>
        |    <GROUP code='10' line='21'>
        |    <VALUE line='22'>0.0</VALUE>
        |    </GROUP>
        |    <GROUP code='20' line='23'>
        |    <VALUE line='24'>0.0</VALUE>
        |    </GROUP>
        |    <GROUP code='30' line='25'>
        |    <VALUE line='26'>0.0</VALUE>
        |    </GROUP>
        |    <GROUP code='3' line='27'>
        |    <VALUE line='28'>*Model_Space</VALUE>
        |    </GROUP>
        |    <GROUP code='1' line='29'>
        |    <VALUE line='30'>*Model_Space</VALUE>
        |    </GROUP>
        |    </GROUPS>
        |    <AFTER>
        |      <GROUPS>
        |      <GROUP code='5' line='33'>
        |      <VALUE line='34'>21</VALUE>
        |      </GROUP>
        |      <GROUP code='330' line='35'>
        |      <VALUE line='36'>1F</VALUE>
        |      </GROUP>
        |      <GROUP code='100' line='37'>
        |      <VALUE line='38'>AcDbEntity</VALUE>
        |      </GROUP>
        |      <GROUP code='8' line='39'>
        |      <VALUE line='40'>0</VALUE>
        |      </GROUP>
        |      <GROUP code='100' line='41'>
        |      <VALUE line='42'>AcDbBlockEnd</VALUE>
        |      </GROUP>
        |      </GROUPS>
        |    </AFTER>
        |  </BLOCK>
        |  </BLOCKS>
        |  </SECTION>
        |</SECTIONS>"""

    val parser: DxfParser = new DxfParser()
    parser.setPrintToXml(true)
//    parser.setPrintDebug(true)
    testByRuleXml(parser, parser.dxf_main, text.stripMargin, xml.stripMargin)
  }
}
