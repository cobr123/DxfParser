package dxf.data

final class DxfHeaderVariable(ctx: dxf.parser.Context, name: String, codeAndValue: List[DxfGroupCodeAndValue]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<VARIABLE name='" + name + "'>" + "\n"
      res += "<GROUPS>" + "\n"
      res += codeAndValue.mkString
      res += "</GROUPS>" + "\n"
      res += "</VARIABLE>" + "\n"
      res
    } else {
      var res = "9" + "\n" + name + "\n"
      res += codeAndValue.mkString
      res
    }
  }
}