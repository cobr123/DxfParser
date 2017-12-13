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
  test("section with classes only to xml") {
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
    val xml =
      """<SECTIONS>
        |  <SECTION name='CLASSES' code='2' line='1'>
        |    <CLASSES>
        |      <CLASS code='0' line='5'>
        |        <GROUPS>
        |        <GROUP code='1' line='7'>
        |        <VALUE line='8'>ACDBDICTIONARYWDFLT</VALUE>
        |        </GROUP>
        |        <GROUP code='2' line='9'>
        |        <VALUE line='10'>AcDbDictionaryWithDefault</VALUE>
        |        </GROUP>
        |        <GROUP code='3' line='11'>
        |        <VALUE line='12'>ObjectDBX Classes</VALUE>
        |        </GROUP>
        |        <GROUP code='90' line='13'>
        |        <VALUE line='14'>0</VALUE>
        |        </GROUP>
        |        <GROUP code='280' line='15'>
        |        <VALUE line='16'>0</VALUE>
        |        </GROUP>
        |        <GROUP code='281' line='17'>
        |        <VALUE line='18'>0</VALUE>
        |        </GROUP>
        |        </GROUPS>
        |      </CLASS>
        |      <CLASS code='0' line='19'>
        |        <GROUPS>
        |        <GROUP code='1' line='21'>
        |        <VALUE line='22'>VISUALSTYLE</VALUE>
        |        </GROUP>
        |        <GROUP code='2' line='23'>
        |        <VALUE line='24'>AcDbVisualStyle</VALUE>
        |        </GROUP>
        |        <GROUP code='3' line='25'>
        |        <VALUE line='26'>ObjectDBX Classes</VALUE>
        |        </GROUP>
        |        <GROUP code='90' line='27'>
        |        <VALUE line='28'>4095</VALUE>
        |        </GROUP>
        |        <GROUP code='280' line='29'>
        |        <VALUE line='30'>0</VALUE>
        |        </GROUP>
        |        <GROUP code='281' line='31'>
        |        <VALUE line='32'>0</VALUE>
        |        </GROUP>
        |        </GROUPS>
        |      </CLASS>
        |    </CLASSES>
        |  </SECTION>
        |</SECTIONS>"""

    val parser: DxfParser = new DxfParser()
    parser.setPrintToXml(true)
    //parser.setPrintDebug(true)
    testByRuleXml(parser, parser.dxf_main, text.stripMargin, xml.stripMargin)
  }
}