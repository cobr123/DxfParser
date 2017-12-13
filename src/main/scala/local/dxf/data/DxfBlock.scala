package local.dxf.data

import local.dxf.parser.Context

import scala.util.parsing.input.Positional

final class DxfBlock(ctx: Context
                     , codeAndDict: List[DxfGroupCodeAndDict]
                     , typeWithGroups: List[DxfTypeWithGroups]
                     , codeAndDictAfter: List[DxfGroupCodeAndDict]
                    ) extends Positional {
  def toDxf(sb: StringBuilder): Unit = {
    sb.append("0\nBLOCK\n")
    for (cad <- codeAndDict) {
      cad.toDxf(sb)
    }
    for (twg <- typeWithGroups) {
      twg.toDxf(sb)
    }
    sb.append("0\nENDBLK\n")
    for (cad <- codeAndDictAfter) {
      cad.toDxf(sb)
    }
    ()
  }

  def toXml(sb: StringBuilder): Unit = {
    sb.append("<BLOCK code='0' line='").append(pos.line).append("'>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cad <- codeAndDict) {
      cad.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    for (twg <- typeWithGroups) {
      twg.toXml(sb)
    }
    sb.append("<AFTER>").append("\n")
    sb.append("<GROUPS>").append("\n")
    for (cad <- codeAndDictAfter) {
      cad.toXml(sb)
    }
    sb.append("</GROUPS>").append("\n")
    sb.append("</AFTER>").append("\n")
    sb.append("</BLOCK>").append("\n")
    ()
  }
}
