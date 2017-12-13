import local.dxf.parser.DxfParser

class TestAllBlocks extends TestUtil {
  test("test all blocks") {
    val text = fileFromResourcesToString("test0.dxf")

    val parser: DxfParser = new DxfParser()
    //parser.setPrintDebug(true)
    testByRule(parser, parser.dxf_main, text)
  }

//    test("test all blocks to xml") {
//      val text = fileFromResourcesToString("test0.dxf")
//
//      val parser: DxfParser = new DxfParser()
//      //parser.setPrintDebug(true)
//      parser.setPrintToXml(true)
//      testByRuleXml(parser, parser.dxf_main, text)
//    }
}