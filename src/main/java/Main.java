import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final int COORDINATE_X = 193;
    private static final int COORDINATE_Y = 706;

    public static void main(String[] args) throws IOException, DocumentException {

        ArrayList<String> invalidList = createFile("pdf/front-invalid.pdf", "pdf/back-invalid.pdf");
        String sourceFile = "result";
        FileOutputStream fos = new FileOutputStream("zip/dirCompressed.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);


        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();

         for(String str : invalidList) {
             File file = new File(str);
             file.deleteOnExit();
         }
    }

    private static void setText(String text, int x, int y, PdfContentByte content) throws IOException, DocumentException {
        BaseFont bfComic = BaseFont.createFont("Calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        content.setFontAndSize(bfComic, 14);
        content.beginText();
        content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 0f);
        content.endText();
        content.stroke();
    }

    private static void setBarCode(String barCode, int x, int y, PdfContentByte content) {
        Barcode128 code128 = new Barcode128();
        code128.setCode(barCode);
        code128.setCodeType(Barcode128.CODE128);
        code128.setSize(5);
        code128.setBarHeight(20);
        code128.setX(0.5f);
        code128.setFont(null);
        PdfTemplate template = code128.createTemplateWithBarcode(
                content, BaseColor.BLACK, BaseColor.BLACK);
        content.addTemplate(template, x, y + 80);
    }

    private static void setImage(String path, PdfContentByte content, int x, int y) throws IOException, DocumentException {
        Image image = Image.getInstance(path);
        image.scaleAbsoluteHeight(100);
        image.scaleAbsoluteWidth(77);
        image.setAbsolutePosition(x, y);
        content.addImage(image);

    }

    private static ArrayList<String> createFile(String frontPath, String backPath) throws IOException, DocumentException {
        String resultPathFront = "result/" + frontPath.substring(4, 17) + "-out.pdf";
        String resultPathBack = "result/" + backPath.substring(4,16) + "-out.pdf";
        String path = frontPath;
        PdfReader pdfReader = new PdfReader(path);
        PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(resultPathFront));
        String path2 = backPath;
        PdfReader pdfReaderBack = new PdfReader(path2);
        PdfStamper back = new PdfStamper(pdfReaderBack, new FileOutputStream(resultPathBack));

        PdfContentByte backContent = back.getOverContent(1);
        PdfContentByte content = stamper.getOverContent(1);
        int coordinateY = COORDINATE_Y;
        int moveY = 163;
        int column = 1;


        for (int i = 0; i < 10; i++) {
            String barCode = "123123123";
            String editedBarCode = barCode.substring(0, 3) + " " + barCode.substring(3,6) + " " + barCode.substring(6);
            String iin = "980916301576";

            String editedIin = barCode.substring(0, 4) + " " + barCode.substring(4,8) + " " + barCode.substring(8);
            if (column == 1) {
                setImage("W-12.jpg", content, COORDINATE_X, coordinateY);
                setText("Антон Павлович", COORDINATE_X - 70, coordinateY - 18, content);
                setText(editedIin, COORDINATE_X + 20, coordinateY - 18, content);

                setBarCode(barCode, COORDINATE_X, coordinateY, backContent);
                setText(editedBarCode, COORDINATE_X - 100, coordinateY + 87, backContent);
                column++;

            } else {
                setImage("W-12.jpg", content,COORDINATE_X * 2 + 86, coordinateY);
                setText(editedIin, COORDINATE_X * 2 + 86 - 70, coordinateY - 18, content);
                setText("980916301576", COORDINATE_X*2 + 86 + 20, coordinateY - 18, content);

                setText(editedBarCode, COORDINATE_X * 2 - 14, coordinateY + 87, backContent);
                setBarCode(barCode, COORDINATE_X*2 + 86, coordinateY, backContent);

                column = 1;
                coordinateY -= moveY;
            }
        }

        back.close();
        stamper.close();
        System.out.println(stamper);

        ArrayList<String> listPath = new ArrayList<>();
        listPath.add(resultPathFront);
        listPath.add(resultPathBack);
        return  listPath;
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
