package local.dxf.data.section

import local.dxf.data.DxfClass
import local.dxf.parser.Context

final class DxfClasses(ctx: Context, classes: List[DxfClass]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='CLASSES' line='" + pos.line + "'>" + "\n"
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
