package dxf.data

final class DxfObjectDictionary(ctx: dxf.parser.Context, codeAndDict: List[DxfGroupCodeAndDict], typeWithGroups: List[DxfTypeWithGroups]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<OBJECT>" + "\n"
      res += codeAndDict.mkString
      res += typeWithGroups.mkString
      res += "</OBJECT>" + "\n"
      res
    } else {
      var res = "0\nDICTIONARY\n"
      res += codeAndDict.mkString
      res += typeWithGroups.mkString
      res
    }
  }
}
