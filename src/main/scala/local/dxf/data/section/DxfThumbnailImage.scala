package local.dxf.data.section

import local.dxf.data.DxfGroupCodeAndValue
import local.dxf.parser.Context

final class DxfThumbnailImage(ctx: Context, codeAndValue: List[DxfGroupCodeAndValue]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='THUMBNAILIMAGE' line='" + pos.line + "'>" + "\n"
      res += "<GROUPS>" + "\n"
      res += codeAndValue.mkString
      res += "</GROUPS>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nTHUMBNAILIMAGE\n"
      res += codeAndValue.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
