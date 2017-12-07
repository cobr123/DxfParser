package dxf.data.section

import dxf.data.DxfTable

final class DxfTables(ctx: dxf.parser.Context, tables: List[DxfTable])extends DxfSection  {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='TABLES'>" + "\n"
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
