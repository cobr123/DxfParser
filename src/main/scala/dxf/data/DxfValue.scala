package dxf.data

final class DxfValue(ctx: dxf.parser.Context, value: String) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<VALUE>" + value + "</VALUE>" + "\n"
      res
    } else {
      value + "\n"
    }
  }
}