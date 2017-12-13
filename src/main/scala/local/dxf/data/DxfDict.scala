package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfDict(ctx: Context, dict_name: String, codeAndValue: List[DxfGroupCodeAndValue], group_code: String) extends Positional with ToDxfXml {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("{").append(dict_name).append("\n")
    for (cav <- codeAndValue) {
      cav.toDxf(sb)
    }
    sb.append(group_code).append("\n")
    sb.append("}").append("\n")
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<DICT name='").append(dict_name).append("' code='").append(group_code).append("' line='").append(pos.line).append("'>").append("\n")
    for (cav <- codeAndValue) {
      cav.toXml(sb)
    }
    sb.append("</DICT>").append("\n")
    ()
  }
}