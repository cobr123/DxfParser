package local.dxf.data.section

import local.dxf.data.DxfTable
import local.dxf.parser.Context

final class DxfTables(ctx: Context, tables: List[DxfTable]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='TABLES' line='" + pos.line + "'>" + "\n"
      res += "<TABLES>" + "\n"
      res += tables.mkString
      res += "</TABLES>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nTABLES\n"
      res += tables.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
