package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfTypeWithGroups(ctx: Context, name: String, codeAndDict: List[DxfGroupCodeAndDict]) extends Positional {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<TYPE name='" + name + "'  line='" + pos.line + "'>" + "\n"
      res += "<GROUPS>" + "\n"
      res += codeAndDict.mkString
      res += "</GROUPS>" + "\n"
      res += "</TYPE>" + "\n"
      res
    } else {
      var res = "0\n" + name + "\n"
      res += codeAndDict.mkString
      res
    }
  }
}
