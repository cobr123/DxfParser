package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfGroupCodeAndValue(ctx: Context, group_code: String, value: Option[DxfValue]) extends Positional {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<GROUP code='" + group_code + "' line='" + pos.line + "'>" + "\n"
      res += value.getOrElse("<VALUE/>" + "\n")
      res += "</GROUP>" + "\n"
      res
    } else {
      var res = group_code + "\n"
      res += value.getOrElse("\n")
      res
    }
  }
}