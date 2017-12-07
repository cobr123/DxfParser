package dxf.parser

final class Context {
  var printToXml = false

  def setPrintToXml(bpPrintToXml: Boolean): Unit = printToXml = bpPrintToXml
}
