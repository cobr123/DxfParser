package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfObjectDictionary(ctx: Context, codeAndDict: List[DxfGroupCodeAndDict], typeWithGroups: List[DxfTypeWithGroups]) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nDICTIONARY\n")
    for (cad <- codeAndDict) {
      cad.toDxf(sb)
    }
    for (twg <- typeWithGroups) {
      twg.toDxf(sb)
    }
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<OBJECT code='0' name='DICTIONARY' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cad <- codeAndDict) {
      cad.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    for (twg <- typeWithGroups) {
      twg.toXml(sb)
    }
    sb.append("</OBJECT>").append("\n")
    ()
  }
}
