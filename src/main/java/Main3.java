import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class Main3 {
    public static void main(String[] args) throws DocumentException, IOException {
        String barCode = "980916301576";

        barCode = barCode.substring(0, 4) + " " + barCode.substring(4,8) + " " + barCode.substring(8);
        System.out.println(barCode);
    }
}
