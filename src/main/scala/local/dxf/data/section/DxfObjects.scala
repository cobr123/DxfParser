package local.dxf.data.section

import local.dxf.data.DxfObjectDictionary
import local.dxf.parser.Context

final class DxfObjects(ctx: Context, objectDictionaries: List[DxfObjectDictionary]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nOBJECTS\n")
    for (objectDictionary <- objectDictionaries) {
      objectDictionary.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='OBJECTS' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<OBJECTS>").append("\n")
    for (objectDictionary <- objectDictionaries) {
      objectDictionary.toXml(sb)
    }
    sb.append("</OBJECTS>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
