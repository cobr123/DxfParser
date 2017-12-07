package dxf.data.section

import dxf.data.DxfTypeWithGroups

final class DxfEntities(ctx: dxf.parser.Context, typeWithGroups: List[DxfTypeWithGroups]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='ENTITIES'>" + "\n"
      res += "<TYPES>" + "\n"
      res += typeWithGroups.mkString
      res += "</TYPES>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nENTITIES\n"
      res += typeWithGroups.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
