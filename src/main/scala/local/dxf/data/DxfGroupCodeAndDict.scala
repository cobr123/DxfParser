package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfGroupCodeAndDict(ctx: Context, group_code: String, dictOrValue: Option[Any]) extends Positional{
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<GROUP code='" + group_code + "' line='" + pos.line + "'>" + "\n"
      res += dictOrValue.getOrElse("<VALUE/>" + "\n")
      res += "</GROUP>" + "\n"
      res
    } else {
      var res = group_code + "\n"
      res += dictOrValue.getOrElse("\n")
      res
    }
  }
}