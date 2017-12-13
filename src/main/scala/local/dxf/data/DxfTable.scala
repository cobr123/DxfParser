package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfTable(ctx: Context, groups: List[DxfGroupCodeAndDict], types: List[DxfTypeWithGroups]) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nTABLE\n")
    for (group <- groups) {
      group.toDxf(sb)
    }
    for (typez <- types) {
      typez.toDxf(sb)
    }
    sb.append("0\nENDTAB\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<TABLE code='0' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (group <- groups) {
      group.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    sb.append("<TYPES>").append("\n")
    for (typez <- types) {
      typez.toXml(sb)
    }
    sb.append("</TYPES>").append("\n")
    sb.append("</TABLE>").append("\n")
    ()
  }
}
