import local.dxf.parser.DxfParser

class TestObjectsBlock extends TestUtil {
  test("section with objects only") {
    val text =
      """  0
        |SECTION
        |  2
        |OBJECTS
        |  0
        |DICTIONARY
        |  5
        |C
        |330
        |0
        |100
        |AcDbDictionary
        |281
        |     1
        |  3
        |ACAD_GROUP
        |350
        |D
        |  3
        |ACAD_LAYOUT
        |350
        |1A
        |  3
        |ACAD_MATERIAL
        |350
        |43
        |  3
        |ACAD_MLEADERSTYLE
        |350
        |6B
        |  3
        |ACAD_MLINESTYLE
        |350
        |17
        |  3
        |ACAD_PLOTSETTINGS
        |350
        |19
        |  3
        |ACAD_PLOTSTYLENAME
        |350
        |E
        |  3
        |ACAD_SCALELIST
        |350
        |47
        |  3
        |ACAD_TABLESTYLE
        |350
        |69
        |  3
        |ACAD_VISUALSTYLE
        |350
        |2A
        |  3
        |AcDbVariableDictionary
        |350
        |A8
        |  0
        |DICTIONARY
        |  5
        |B2
        |330
        |8
        |100
        |AcDbDictionary
        |280
        |     1
        |281
        |     1
        |  3
        |ACAD_XREC_ROUNDTRIP
        |360
        |B3
        |  0
        |DICTIONARY
        |  5
        |D
        |102
        |{ACAD_REACTORS
        |330
        |C
        |102
        |}
        |330
        |C
        |100
        |AcDbDictionary
        |281
        |     1
        |  0
        |LAYOUT
        |  5
        |22
        |102
        |{ACAD_REACTORS
        |330
        |1A
        |102
        |}
        |330
        |1A
        |100
        |AcDbPlotSettings
        |  1
        |
        |  2
        |none_device
        |  4
        |Letter_(8.50_x_11.00_Inches)
        |  6
        |
        | 40
        |6.35
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
  test("section with objects only to xml") {
    val text =
      """  0
        |SECTION
        |  2
        |OBJECTS
        |  0
        |DICTIONARY
        |  5
        |C
        |330
        |0
        |100
        |AcDbDictionary
        |281
        |     1
        |  3
        |ACAD_GROUP
        |350
        |D
        |  3
        |ACAD_LAYOUT
        |350
        |1A
        |  3
        |ACAD_MATERIAL
        |350
        |43
        |  3
        |ACAD_MLEADERSTYLE
        |350
        |6B
        |  3
        |ACAD_MLINESTYLE
        |350
        |17
        |  3
        |ACAD_PLOTSETTINGS
        |350
        |19
        |  3
        |ACAD_PLOTSTYLENAME
        |350
        |E
        |  3
        |ACAD_SCALELIST
        |350
        |47
        |  3
        |ACAD_TABLESTYLE
        |350
        |69
        |  3
        |ACAD_VISUALSTYLE
        |350
        |2A
        |  3
        |AcDbVariableDictionary
        |350
        |A8
        |  0
        |DICTIONARY
        |  5
        |B2
        |330
        |8
        |100
        |AcDbDictionary
        |280
        |     1
        |281
        |     1
        |  3
        |ACAD_XREC_ROUNDTRIP
        |360
        |B3
        |  0
        |DICTIONARY
        |  5
        |D
        |102
        |{ACAD_REACTORS
        |330
        |C
        |102
        |}
        |330
        |C
        |100
        |AcDbDictionary
        |281
        |     1
        |  0
        |LAYOUT
        |  5
        |22
        |102
        |{ACAD_REACTORS
        |330
        |1A
        |102
        |}
        |330
        |1A
        |100
        |AcDbPlotSettings
        |  1
        |
        |  2
        |none_device
        |  4
        |Letter_(8.50_x_11.00_Inches)
        |  6
        |
        | 40
        |6.35
        |  0
        |ENDSEC
        |  0
        |EOF
        |
        |  """
    val xml =
      """<SECTIONS>
        |<SECTION name='OBJECTS' code='2' line='1'>
        |<OBJECTS>
        |<OBJECT code='0' name='DICTIONARY' line='5'>
        |<GROUPS>
        |<GROUP code='5' line='7'>
        |<VALUE line='8'>C</VALUE>
        |</GROUP>
        |<GROUP code='330' line='9'>
        |<VALUE line='10'>0</VALUE>
        |</GROUP>
        |<GROUP code='100' line='11'>
        |<VALUE line='12'>AcDbDictionary</VALUE>
        |</GROUP>
        |<GROUP code='281' line='13'>
        |<VALUE line='14'>1</VALUE>
        |</GROUP>
        |<GROUP code='3' line='15'>
        |<VALUE line='16'>ACAD_GROUP</VALUE>
        |</GROUP>
        |<GROUP code='350' line='17'>
        |<VALUE line='18'>D</VALUE>
        |</GROUP>
        |<GROUP code='3' line='19'>
        |<VALUE line='20'>ACAD_LAYOUT</VALUE>
        |</GROUP>
        |<GROUP code='350' line='21'>
        |<VALUE line='22'>1A</VALUE>
        |</GROUP>
        |<GROUP code='3' line='23'>
        |<VALUE line='24'>ACAD_MATERIAL</VALUE>
        |</GROUP>
        |<GROUP code='350' line='25'>
        |<VALUE line='26'>43</VALUE>
        |</GROUP>
        |<GROUP code='3' line='27'>
        |<VALUE line='28'>ACAD_MLEADERSTYLE</VALUE>
        |</GROUP>
        |<GROUP code='350' line='29'>
        |<VALUE line='30'>6B</VALUE>
        |</GROUP>
        |<GROUP code='3' line='31'>
        |<VALUE line='32'>ACAD_MLINESTYLE</VALUE>
        |</GROUP>
        |<GROUP code='350' line='33'>
        |<VALUE line='34'>17</VALUE>
        |</GROUP>
        |<GROUP code='3' line='35'>
        |<VALUE line='36'>ACAD_PLOTSETTINGS</VALUE>
        |</GROUP>
        |<GROUP code='350' line='37'>
        |<VALUE line='38'>19</VALUE>
        |</GROUP>
        |<GROUP code='3' line='39'>
        |<VALUE line='40'>ACAD_PLOTSTYLENAME</VALUE>
        |</GROUP>
        |<GROUP code='350' line='41'>
        |<VALUE line='42'>E</VALUE>
        |</GROUP>
        |<GROUP code='3' line='43'>
        |<VALUE line='44'>ACAD_SCALELIST</VALUE>
        |</GROUP>
        |<GROUP code='350' line='45'>
        |<VALUE line='46'>47</VALUE>
        |</GROUP>
        |<GROUP code='3' line='47'>
        |<VALUE line='48'>ACAD_TABLESTYLE</VALUE>
        |</GROUP>
        |<GROUP code='350' line='49'>
        |<VALUE line='50'>69</VALUE>
        |</GROUP>
        |<GROUP code='3' line='51'>
        |<VALUE line='52'>ACAD_VISUALSTYLE</VALUE>
        |</GROUP>
        |<GROUP code='350' line='53'>
        |<VALUE line='54'>2A</VALUE>
        |</GROUP>
        |<GROUP code='3' line='55'>
        |<VALUE line='56'>AcDbVariableDictionary</VALUE>
        |</GROUP>
        |<GROUP code='350' line='57'>
        |<VALUE line='58'>A8</VALUE>
        |</GROUP>
        |</GROUPS>
        |</OBJECT>
        |<OBJECT code='0' name='DICTIONARY' line='59'>
        |<GROUPS>
        |<GROUP code='5' line='61'>
        |<VALUE line='62'>B2</VALUE>
        |</GROUP>
        |<GROUP code='330' line='63'>
        |<VALUE line='64'>8</VALUE>
        |</GROUP>
        |<GROUP code='100' line='65'>
        |<VALUE line='66'>AcDbDictionary</VALUE>
        |</GROUP>
        |<GROUP code='280' line='67'>
        |<VALUE line='68'>1</VALUE>
        |</GROUP>
        |<GROUP code='281' line='69'>
        |<VALUE line='70'>1</VALUE>
        |</GROUP>
        |<GROUP code='3' line='71'>
        |<VALUE line='72'>ACAD_XREC_ROUNDTRIP</VALUE>
        |</GROUP>
        |<GROUP code='360' line='73'>
        |<VALUE line='74'>B3</VALUE>
        |</GROUP>
        |</GROUPS>
        |</OBJECT>
        |<OBJECT code='0' name='DICTIONARY' line='75'>
        |<GROUPS>
        |<GROUP code='5' line='77'>
        |<VALUE line='78'>D</VALUE>
        |</GROUP>
        |<GROUP code='102' line='79'>
        |<DICT name='ACAD_REACTORS' code='102' line='80'>
        |<GROUP code='330' line='81'>
        |<VALUE line='82'>C</VALUE>
        |</GROUP>
        |</DICT>
        |</GROUP>
        |<GROUP code='330' line='85'>
        |<VALUE line='86'>C</VALUE>
        |</GROUP>
        |<GROUP code='100' line='87'>
        |<VALUE line='88'>AcDbDictionary</VALUE>
        |</GROUP>
        |<GROUP code='281' line='89'>
        |<VALUE line='90'>1</VALUE>
        |</GROUP>
        |</GROUPS>
        |<TYPE code='0' name='LAYOUT' line='91'>
        |<GROUPS>
        |<GROUP code='5' line='93'>
        |<VALUE line='94'>22</VALUE>
        |</GROUP>
        |<GROUP code='102' line='95'>
        |<DICT name='ACAD_REACTORS' code='102' line='96'>
        |<GROUP code='330' line='97'>
        |<VALUE line='98'>1A</VALUE>
        |</GROUP>
        |</DICT>
        |</GROUP>
        |<GROUP code='330' line='101'>
        |<VALUE line='102'>1A</VALUE>
        |</GROUP>
        |<GROUP code='100' line='103'>
        |<VALUE line='104'>AcDbPlotSettings</VALUE>
        |</GROUP>
        |<GROUP code='1' line='105'>
        |<VALUE line='106'></VALUE>
        |</GROUP>
        |<GROUP code='2' line='107'>
        |<VALUE line='108'>none_device</VALUE>
        |</GROUP>
        |<GROUP code='4' line='109'>
        |<VALUE line='110'>Letter_(8.50_x_11.00_Inches)</VALUE>
        |</GROUP>
        |<GROUP code='6' line='111'>
        |<VALUE line='112'></VALUE>
        |</GROUP>
        |<GROUP code='40' line='113'>
        |<VALUE line='114'>6.35</VALUE>
        |</GROUP>
        |</GROUPS>
        |</TYPE>
        |</OBJECT>
        |</OBJECTS>
        |</SECTION>
        |</SECTIONS>"""

    val parser: DxfParser = new DxfParser()
    parser.setPrintToXml(true)
    //parser.setPrintDebug(true)
    testByRuleXml(parser, parser.dxf_main, text.stripMargin, xml.stripMargin)
  }
}
