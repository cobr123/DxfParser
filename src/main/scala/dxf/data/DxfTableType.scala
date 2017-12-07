package dxf.data

final class DxfTableType(ctx: dxf.parser.Context, name: String, codeAndDict: List[DxfGroupCodeAndDict]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<TYPE name='" + name + "'>" + "\n"
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
