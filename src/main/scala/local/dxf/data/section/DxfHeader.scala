package local.dxf.data.section

import local.dxf.data.DxfHeaderVariable
import local.dxf.parser.Context

final class DxfHeader(ctx: Context, variables: List[DxfHeaderVariable]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='HEADER' line='" + pos.line + "'>" + "\n"
      res += "<VARIABLES>" + "\n"
      res += variables.mkString
      res += "</VARIABLES>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nHEADER\n"
      res += variables.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
