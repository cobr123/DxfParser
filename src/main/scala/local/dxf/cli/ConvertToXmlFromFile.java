package local.dxf.cli;

import org.apache.commons.io.FileUtils;
import local.dxf.parser.DxfParser;

import java.io.File;
import java.io.IOException;

final public class ConvertToXmlFromFile {
    /*
     * Принимает параметры командной строки:
     * первый парамерт - путь к файлу с исходным текстом для разбора
     * второй параметр - путь к файлу для выровненного текста скрипта
     * третий параметр - путь к файлу для текста ошибки
     * */
    public static void main(String args[]) throws IOException {
        final String inFile = args[0];
        final String outFile = args[1];
        final String errFile = args[2];
        convertDxfToXml(inFile, outFile, errFile);
    }

    public static void convertDxfToXml(String inFile, String outFile, String errFile) throws IOException {
        final String text = FileUtils.readFileToString(new File(inFile), "UTF-8");

        if (null != text && !text.trim().isEmpty()) {
            final DxfParser parser = new DxfParser();

            try {
                final Object[] res = parser.convertToXml(text);
                if (Integer.valueOf(String.valueOf(res[0])) == 0) {
                    final String xmlText = String.valueOf(res[1]);
                    FileUtils.writeStringToFile(new File(outFile), xmlText, "UTF-8");
                } else {
                    final String errText = String.valueOf(res[1]);
                    FileUtils.writeStringToFile(new File(errFile), errText, "UTF-8");
                }
            } catch (final Exception e) {
                FileUtils.writeStringToFile(new File(errFile), e.getLocalizedMessage(), "UTF-8");
            }
        }
    }
}
