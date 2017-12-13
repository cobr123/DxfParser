package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfValue(ctx: Context, value: String) extends Positional with ToDxfXml {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append(value).append("\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<VALUE line='").append(pos.line).append("'>").append(value).append("</VALUE>").append("\n")
    ()
  }
}