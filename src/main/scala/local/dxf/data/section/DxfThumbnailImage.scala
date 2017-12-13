package local.dxf.data.section

import local.dxf.data.DxfGroupCodeAndValue
import local.dxf.parser.Context

final class DxfThumbnailImage(ctx: Context, codeAndValue: List[DxfGroupCodeAndValue]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nTHUMBNAILIMAGE\n")
    for (cav <- codeAndValue) {
      cav.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }
  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='THUMBNAILIMAGE' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cav <- codeAndValue) {
      cav.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
