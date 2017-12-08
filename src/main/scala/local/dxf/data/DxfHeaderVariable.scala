package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfHeaderVariable(ctx: Context, name: String, codeAndValue: List[DxfGroupCodeAndValue]) extends Positional {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<VARIABLE name='" + name + "' line='" + pos.line + "'>" + "\n"
      res += "<GROUPS>" + "\n"
      res += codeAndValue.mkString
      res += "</GROUPS>" + "\n"
      res += "</VARIABLE>" + "\n"
      res
    } else {
      var res = "9" + "\n" + name + "\n"
      res += codeAndValue.mkString
      res
    }
  }
}