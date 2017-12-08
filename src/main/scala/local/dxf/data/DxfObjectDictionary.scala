package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfObjectDictionary(ctx: Context, codeAndDict: List[DxfGroupCodeAndDict], typeWithGroups: List[DxfTypeWithGroups]) extends Positional {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<OBJECT  line='" + pos.line + "'>" + "\n"
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
