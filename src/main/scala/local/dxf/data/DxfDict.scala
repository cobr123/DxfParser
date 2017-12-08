package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfDict(ctx: Context, dict_name: String, codeAndValue: List[DxfGroupCodeAndValue], group_code: String) extends Positional {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<DICT name='" + dict_name + "' code='" + group_code + "' line='" + pos.line + "'>" + "\n"
      res += codeAndValue.mkString
      res += "</DICT>" + "\n"
      res
    } else {
      var res = "{" + dict_name + "\n"
      res += codeAndValue.mkString
      res += group_code + "\n"
      res += "}" + "\n"
      res
    }
  }
}