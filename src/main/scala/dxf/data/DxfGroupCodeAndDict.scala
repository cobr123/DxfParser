package dxf.data

final class DxfGroupCodeAndDict(ctx: dxf.parser.Context, group_code: String, dictOrValue: Option[Any]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<GROUP code='" + group_code + "'>" + "\n"
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