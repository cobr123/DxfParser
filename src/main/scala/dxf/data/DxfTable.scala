package dxf.data


final class DxfTable(ctx: dxf.parser.Context, groups: List[DxfGroupCodeAndDict], types: List[DxfTableType]) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<TABLE>" + "\n"
      res += "<GROUPS>" + "\n"
      res += groups.mkString
      res += "</GROUPS>" + "\n"
      res += "<TYPES>" + "\n"
      res += types.mkString
      res += "</TYPES>" + "\n"
      res += "</TABLE>" + "\n"
      res
    } else {
      var res = "0\nTABLE\n"
      res += groups.mkString
      res += types.mkString
      res += "0\nENDTAB\n"
      res
    }
  }
}
