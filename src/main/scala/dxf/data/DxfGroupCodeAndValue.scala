package dxf.data

final class DxfGroupCodeAndValue(ctx: dxf.parser.Context, group_code: String, value: Option[DxfValue]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<GROUP code='" + group_code + "'>" + "\n"
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