package local.dxf.data.section

import local.dxf.data.DxfClass
import local.dxf.parser.Context

final class DxfClasses(ctx: Context, classes: List[DxfClass]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nCLASSES\n")
    for (clazz <- classes) {
      clazz.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }
  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='CLASSES' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<CLASSES>").append("\n")
    for (clazz <- classes) {
      clazz.toXml(sb)
    }
    sb.append("</CLASSES>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
