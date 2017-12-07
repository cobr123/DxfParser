package dxf.data.section

import dxf.data.DxfHeaderVariable

final class DxfHeader(ctx: dxf.parser.Context, variables: List[DxfHeaderVariable]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='HEADER'>" + "\n"
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
