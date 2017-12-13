package local.dxf.data.section

import local.dxf.data.DxfBlock
import local.dxf.parser.Context

final class DxfBlocks(ctx: Context, classes: List[DxfBlock]) extends DxfSection {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nSECTION\n2\nBLOCKS\n")
    for (clazz <- classes) {
      clazz.toDxf(sb)
    }
    sb.append("0\nENDSEC\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<SECTION name='BLOCKS' code='2' line='").append(pos.line).append("'>").append("\n")
    sb.append("<BLOCKS>").append("\n")
    for (clazz <- classes) {
      clazz.toXml(sb)
    }
    sb.append("</BLOCKS>").append("\n")
    sb.append("</SECTION>").append("\n")
    ()
  }
}
