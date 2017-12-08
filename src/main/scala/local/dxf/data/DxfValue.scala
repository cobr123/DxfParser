package local.dxf.data

import local.dxf.parser.Context

final class DxfValue(ctx: Context, value: String) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<VALUE>" + value + "</VALUE>" + "\n"
      res
    } else {
      value + "\n"
    }
  }
}