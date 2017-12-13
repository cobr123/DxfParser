package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfTypeWithGroups(ctx: Context, name: String, codeAndDict: List[DxfGroupCodeAndDict]) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\n").append(name).append("\n")
    for (cad <- codeAndDict) {
      cad.toDxf(sb)
    }
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<TYPE code='0' name='").append(name).append("' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cad <- codeAndDict) {
      cad.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    sb.append("</TYPE>").append("\n")
    ()
  }
}
