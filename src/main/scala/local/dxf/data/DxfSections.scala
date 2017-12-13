package local.dxf.data

import local.dxf.data.section.DxfSection
import local.dxf.parser.Context

final class DxfSections(ctx: Context, sections: List[Option[DxfSection]]) extends ToDxfXml {
  def toDxf(sb: StringBuilder): Unit = {
    for (section <- sections) {
      if (section.isDefined) {
        section.get.toDxf(sb)
      }
    }
    sb.append("0\nEOF\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTIONS>").append("\n")
    for (section <- sections) {
      if (section.isDefined) {
        section.get.toXml(sb)
      }
    }
    sb.append("</SECTIONS>").append("\n")
    ()
  }
}
