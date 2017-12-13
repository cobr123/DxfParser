import local.dxf.parser.DxfParser

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
  test("section with tables only to xml") {
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
    val xml =
      """<SECTIONS>
        |  <SECTION name='TABLES' code='2' line='1'>
        |    <TABLES>
        |      <TABLE code='0' line='5'>
        |        <GROUPS>
        |          <GROUP code='2' line='7'>
        |          <VALUE line='8'>VPORT</VALUE>
        |          </GROUP>
        |          <GROUP code='5' line='9'>
        |          <VALUE line='10'>8</VALUE>
        |          </GROUP>
        |          <GROUP code='102' line='11'>
        |          <DICT name='ACAD_XDICTIONARY' code='102' line='12'>
        |          <GROUP code='360' line='13'>
        |          <VALUE line='14'>B2</VALUE>
        |          </GROUP>
        |          </DICT>
        |          </GROUP>
        |          <GROUP code='330' line='17'>
        |          <VALUE line='18'>0</VALUE>
        |          </GROUP>
        |          <GROUP code='100' line='19'>
        |          <VALUE line='20'>AcDbSymbolTable</VALUE>
        |          </GROUP>
        |          <GROUP code='70' line='21'>
        |          <VALUE line='22'>1</VALUE>
        |          </GROUP>
        |        </GROUPS>
        |        <TYPES>
        |          <TYPE code='0' name='VPORT'  line='23'>
        |            <GROUPS>
        |              <GROUP code='5' line='25'>
        |              <VALUE line='26'>29</VALUE>
        |              </GROUP>
        |              <GROUP code='330' line='27'>
        |              <VALUE line='28'>8</VALUE>
        |              </GROUP>
        |              <GROUP code='100' line='29'>
        |              <VALUE line='30'>AcDbSymbolTableRecord</VALUE>
        |              </GROUP>
        |              <GROUP code='100' line='31'>
        |              <VALUE line='32'>AcDbViewportTableRecord</VALUE>
        |              </GROUP>
        |              <GROUP code='2' line='33'>
        |              <VALUE line='34'>*Active</VALUE>
        |              </GROUP>
        |              <GROUP code='70' line='35'>
        |              <VALUE line='36'>0</VALUE>
        |              </GROUP>
        |              <GROUP code='10' line='37'>
        |              <VALUE line='38'>0.0</VALUE>
        |              </GROUP>
        |              <GROUP code='20' line='39'>
        |              <VALUE line='40'>0.0</VALUE>
        |              </GROUP>
        |              <GROUP code='11' line='41'>
        |              <VALUE line='42'>1.0</VALUE>
        |              </GROUP>
        |            </GROUPS>
        |          </TYPE>
        |        </TYPES>
        |      </TABLE>
        |    </TABLES>
        |  </SECTION>
        |</SECTIONS>"""

    val parser: DxfParser = new DxfParser()
    parser.setPrintToXml(true)
    //    parser.setPrintDebug(true)
    testByRuleXml(parser, parser.dxf_main, text.stripMargin, xml.stripMargin)
  }
}