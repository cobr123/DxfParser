package local.dxf.data.section

import local.dxf.data.DxfTypeWithGroups
import local.dxf.parser.Context

final class DxfEntities(ctx: Context, typeWithGroups: List[DxfTypeWithGroups]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nENTITIES\n")
    for (twg <- typeWithGroups) {
      twg.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='ENTITIES' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<TYPES>").append("\n")
    for (twg <- typeWithGroups) {
      twg.toXml(sb)
    }
    sb.append("</TYPES>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
