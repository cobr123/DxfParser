import local.dxf.parser.DxfParser

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
        |   """

    val parser: DxfParser = new DxfParser()
    //parser.setPrintDebug(true)
    testByRule(parser, parser.dxf_main, text.stripMargin)
  }

  test("section with header only to xml") {
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
        |$INSBASE
        | 10
        |0.1
        | 20
        |0.2
        | 30
        |0.3
        |  9
        |$DWGCODEPAGE
        |  3
        |ANSI_1251
        |  2
        |{C4759B25-B8D2-4FD4-839A-6A86C9E393CB}
        |  0
        |ENDSEC
        |  0
        |EOF
        |
        |   """

    val xml =
      """<SECTIONS>
        |  <SECTION name='HEADER' code='2' line='1'>
        |    <VARIABLES>
        |      <VARIABLE name='$ACADVER' code='9' line='5'>
        |        <GROUPS>
        |          <GROUP code='1' line='7'>
        |            <VALUE line='8'>AC1021</VALUE>
        |          </GROUP>
        |        </GROUPS>
        |      </VARIABLE>
        |      <VARIABLE name='$INSBASE' code='9' line='9'>
        |        <GROUPS>
        |          <GROUP code='10' line='11'>
        |            <VALUE line='12'>0.1</VALUE>
        |          </GROUP>
        |          <GROUP code='20' line='13'>
        |            <VALUE line='14'>0.2</VALUE>
        |          </GROUP>
        |          <GROUP code='30' line='15'>
        |            <VALUE line='16'>0.3</VALUE>
        |          </GROUP>
        |        </GROUPS>
        |      </VARIABLE>
        |      <VARIABLE name='$DWGCODEPAGE' code='9' line='17'>
        |        <GROUPS>
        |          <GROUP code='3' line='19'>
        |            <VALUE line='20'>ANSI_1251</VALUE>
        |          </GROUP>
        |          <GROUP code='2' line='21'>
        |            <VALUE line='22'>{C4759B25-B8D2-4FD4-839A-6A86C9E393CB}</VALUE>
        |          </GROUP>
        |        </GROUPS>
        |      </VARIABLE>
        |    </VARIABLES>
        |  </SECTION>
        |</SECTIONS>"""

    val parser: DxfParser = new DxfParser()
    parser.setPrintToXml(true)
    //parser.setPrintDebug(true)
    testByRuleXml(parser, parser.dxf_main, text.stripMargin, xml.stripMargin)
  }
}