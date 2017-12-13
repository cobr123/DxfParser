package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfGroupCodeAndValue(ctx: Context, group_code: String, value: DxfValue) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append(group_code).append("\n")
    value.toDxf(sb)
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<GROUP code='").append(group_code).append("' line='").append(pos.line).append("'>").append("\n")
    value.toXml(sb)
    sb.append("</GROUP>").append("\n")
    ()
  }
}