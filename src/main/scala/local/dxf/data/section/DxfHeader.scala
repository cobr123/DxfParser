package local.dxf.data.section

import local.dxf.data.DxfHeaderVariable
import local.dxf.parser.Context

final class DxfHeader(ctx: Context, variables: List[DxfHeaderVariable]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nHEADER\n")
    for (variable <- variables) {
      variable.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='HEADER' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<VARIABLES>").append("\n")
    for (variable <- variables) {
      variable.toXml(sb)
    }
    sb.append("</VARIABLES>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
