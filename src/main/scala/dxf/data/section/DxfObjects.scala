package dxf.data.section

import dxf.data.DxfObjectDictionary

final class DxfObjects(ctx: dxf.parser.Context, objectDictionaries: List[DxfObjectDictionary]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='OBJECTS'>" + "\n"
      res += "<OBJECTS>" + "\n"
      res += objectDictionaries.mkString
      res += "</OBJECTS>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nOBJECTS\n"
      res += objectDictionaries.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
