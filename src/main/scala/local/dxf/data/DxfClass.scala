package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfClass(ctx: Context, codeAndDict: List[DxfGroupCodeAndDict]) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nCLASS\n")
    for (cad <- codeAndDict) {
      cad.toDxf(sb)
    }
    ()
  }
  def toXml(sb: StringBuilder): Unit = {
    sb.append("<CLASS code='0' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cad <- codeAndDict) {
      cad.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    sb.append("</CLASS>").append("\n")
    ()
  }
}
