package local.dxf.data

import local.dxf.parser.Context

final class DxfSections(ctx: Context, sections: List[Option[Any]]) { //DxfSection
  override def toString: String = {
    if (ctx.printToXml) {
      var res = "<SECTIONS>" + "\n"
      for (section <- sections) {
        if (section.isDefined) {
          res += section.get
        }
      }
      res += "</SECTIONS>" + "\n"
      res
    } else {
      var res = ""
      for (section <- sections) {
        if (section.isDefined) {
          res += section.get
        }
      }
      res += "0\nEOF\n"
      res
    }
  }
}
