package dxf.data

final class DxfDict(ctx: dxf.parser.Context, dict_name: String, codeAndValue: List[DxfGroupCodeAndValue], group_code: String) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<DICT name='" + dict_name + "' code='" + group_code + "'>" + "\n"
      res += codeAndValue.mkString
      res += "</DICT>" + "\n"
      res
    } else {
      var res = "{" + dict_name + "\n"
      res += codeAndValue.mkString
      res += group_code + "\n"
      res += "}" + "\n"
      res
    }
  }
}