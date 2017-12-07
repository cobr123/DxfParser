package dxf.data

final class DxfBlock(ctx: dxf.parser.Context
                     , codeAndDict: List[DxfGroupCodeAndDict]
                     , typeWithGroups: List[DxfTypeWithGroups]
                     , codeAndDictAfter: List[DxfGroupCodeAndDict]
                    ) {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<BLOCK>" + "\n"
      res += codeAndDict.mkString
      res += typeWithGroups.mkString
      res += "<AFTER>" + "\n"
      res += codeAndDictAfter.mkString
      res += "</AFTER>" + "\n"
      res += "</BLOCK>" + "\n"
      res
    } else {
      var res = "0\nBLOCK\n"
      res += codeAndDict.mkString
      res += typeWithGroups.mkString
      res += "0\nENDBLK\n"
      res += codeAndDictAfter.mkString
      res
    }
  }
}
