package dxf.data.section

import dxf.data.DxfClass

final class DxfClasses(ctx: dxf.parser.Context, classes: List[DxfClass]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='CLASSES'>" + "\n"
      res += "<CLASSES>" + "\n"
      res += classes.mkString
      res += "</CLASSES>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nCLASSES\n"
      res += classes.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
