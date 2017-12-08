package local.dxf.data

import local.dxf.parser.Context

final class DxfClass(ctx: Context, codeAndDict: List[DxfGroupCodeAndDict], typeWithGroups: List[DxfTypeWithGroups]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<CLASS>" + "\n"
      res += codeAndDict.mkString
      res += typeWithGroups.mkString
      res += "</CLASS>" + "\n"
      res
    } else {
      var res = "0\nCLASS\n"
      res += codeAndDict.mkString
      res += typeWithGroups.mkString
      res
    }
  }
}
