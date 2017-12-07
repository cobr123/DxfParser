package dxf.data.section

import dxf.data.DxfGroupCodeAndValue

final class DxfThumbnailImage(ctx: dxf.parser.Context, codeAndValue: List[DxfGroupCodeAndValue])extends DxfSection  {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='THUMBNAILIMAGE'>" + "\n"
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
