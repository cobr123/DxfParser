package local.dxf.data.section

import local.dxf.data.DxfTable
import local.dxf.parser.Context

final class DxfTables(ctx: Context, tables: List[DxfTable]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nTABLES\n")
    for (table <- tables) {
      table.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }
  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='TABLES' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<TABLES>").append("\n")
    for (table <- tables) {
      table.toXml(sb)
    }
    sb.append("</TABLES>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
