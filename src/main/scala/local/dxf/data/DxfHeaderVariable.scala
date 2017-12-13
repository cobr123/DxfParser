package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfHeaderVariable(ctx: Context, name: String, codeAndValue: List[DxfGroupCodeAndValue]) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("9").append("\n").append(name).append("\n")
    for (cav <- codeAndValue) {
      cav.toDxf(sb)
    }
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<VARIABLE name='").append(name).append("' code='9' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cav <- codeAndValue) {
      cav.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    sb.append("</VARIABLE>").append("\n")
    ()
  }
}