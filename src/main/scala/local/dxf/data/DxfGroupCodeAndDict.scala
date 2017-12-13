package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfGroupCodeAndDict(ctx: Context, group_code: String, dictOrValue: ToDxfXml) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append(group_code).append("\n")
    dictOrValue.toDxf(sb)
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<GROUP code='").append(group_code).append("' line='").append(pos.line).append("'>").append("\n")
    dictOrValue.toXml(sb)
    sb.append("</GROUP>").append("\n")
    ()
  }
}