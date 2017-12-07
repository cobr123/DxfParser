
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
}
