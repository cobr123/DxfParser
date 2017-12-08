package local.dxf.data.section

import local.dxf.data.DxfBlock
import local.dxf.parser.Context

final class DxfBlocks(ctx: Context, classes: List[DxfBlock]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='BLOCKS' line='" + pos.line + "'>" + "\n"
      res += "<BLOCKS>" + "\n"
      res += classes.mkString
      res += "</BLOCKS>" + "\n"
      res += "</SECTION>" + "\n"
      res
    } else {
      var res = "0\nSECTION\n2\nBLOCKS\n"
      res += classes.mkString
      res += "0\nENDSEC\n"
      res
    }
  }
}
