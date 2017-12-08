package local.dxf.data.section

import local.dxf.data.DxfTypeWithGroups
import local.dxf.parser.Context

final class DxfEntities(ctx: Context, typeWithGroups: List[DxfTypeWithGroups]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='ENTITIES' line='" + pos.line + "'>" + "\n"
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
