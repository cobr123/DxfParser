package dxf.data.section

import dxf.data.DxfBlock

final class DxfBlocks(ctx: dxf.parser.Context, classes: List[DxfBlock]) extends DxfSection {
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTION name='BLOCKS'>" + "\n"
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
