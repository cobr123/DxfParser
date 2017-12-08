package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfTable(ctx: Context, groups: List[DxfGroupCodeAndDict], types: List[DxfTypeWithGroups]) extends Positional {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<TABLE  line='" + pos.line + "'>" + "\n"
      res += "<GROUPS>" + "\n"
      res += groups.mkString
      res += "</GROUPS>" + "\n"
      res += "<TYPES>" + "\n"
      res += types.mkString
      res += "</TYPES>" + "\n"
      res += "</TABLE>" + "\n"
      res
    } else {
      var res = "0\nTABLE\n"
      res += groups.mkString
      res += types.mkString
      res += "0\nENDTAB\n"
      res
    }
  }
}
