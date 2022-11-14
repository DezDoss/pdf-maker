import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Udostoverenie {
    static long bar = 71217059001L;
    private static final int COORDINATE_X = 193;
    private static final int COORDINATE_Y = 706;

    public static void main(String[] args) throws IOException, DocumentException {

        ArrayList<String> invalidList = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
        invalidList.addAll(createFile(1));

//        }
        String sourceFile = "result";
        FileOutputStream fos = new FileOutputStream("zip/staff.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);


        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();

        for (String str : invalidList) {
            File file = new File(str);
            file.deleteOnExit();
        }
    }

    private static ArrayList<String> createFile(int j) throws IOException, DocumentException {
        String resultPathFront = "result/" + j + "-out.pdf";
        ArrayList<String> listPath = new ArrayList<>();

        String resultPathBack = "result/" + j + "-out.pdf";
        String path = "blanks/blank/setka.pdf";
        PdfReader pdfReader = new PdfReader(path);
        PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(resultPathFront));
        String path2 = "blanks/blank/setka.pdf";
        PdfReader pdfReaderBack = new PdfReader(path2);
        PdfStamper back = new PdfStamper(pdfReaderBack, new FileOutputStream(resultPathBack));

        PdfContentByte backContent = back.getOverContent(1);
        PdfContentByte content = stamper.getOverContent(1);
        int coordinateY = COORDINATE_Y;
        int moveY = 163;
        int column = 1;

        for (int i = 1; i <= 4; i++) {
          String blankBackPath = "staff/"+ i + ".png";
          if (column == 1) {
                setImage(blankBackPath, COORDINATE_X * 2 + 86 - 160, coordinateY - 40, backContent);
                column++;

            } else {
                setImage(blankBackPath, COORDINATE_X - 159, coordinateY - 40, backContent);
                column = 1;
                coordinateY -= moveY;
            }
//         }


        }
        listPath.add(resultPathBack);
        back.close();
//            stamper.close();
        return listPath;
    }

    private static void setText(String text, float x, float y, PdfContentByte content, int fontSize) throws IOException, DocumentException {
        BaseFont bfComic = BaseFont.createFont("Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        content.setFontAndSize(bfComic, fontSize);
        content.beginText();
//        content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 0f);
        content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 270f);
        content.endText();
        content.stroke();
    }

    private static void setQRCode(String barCode, int x, float y, PdfContentByte content) throws DocumentException {
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(barCode, 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        Image mask = barcodeQRCode.getImage();
        mask.makeMask();
        codeQrImage.setImageMask(mask);
        codeQrImage.scaleAbsolute(45, 45);
        codeQrImage.setAbsolutePosition(x, y);
        content.addImage(codeQrImage);
    }

    private static void setBarCode(String barCode, int x, int y, PdfContentByte content) {
        Barcode128 code128 = new Barcode128();
        code128.setCode(barCode);
        code128.setCodeType(Barcode128.CODE128);
        code128.setSize(5);
        code128.setBarHeight(20);
        code128.setX(0.8f);
        code128.setFont(null);
        PdfTemplate template = code128.createTemplateWithBarcode(
                content, BaseColor.BLACK, BaseColor.BLACK);
        content.addTemplate(template, x, y + 80);
    }

    private static void setPhoto(String path, PdfContentByte content, int x, int y) throws IOException, DocumentException {
        Image image = Image.getInstance(path);
        image.scaleAbsoluteHeight(100);
        image.scaleAbsoluteWidth(75);
        image.setAbsolutePosition(x, y);
        content.addImage(image);

    }

    private static void setImage(String path, int x, int y, PdfContentByte content) throws IOException, DocumentException {
        Image image = Image.getInstance(path);
        image.scaleAbsoluteHeight(162);
        image.scaleAbsoluteWidth(254);
        image.setAbsolutePosition(x, y);
        content.addImage(image);

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

    public static String defineFront(int i) {
        if (i == 0 || i == 1) {
            return "newnew/green-front.png";
        } else if (i == 2 || i == 3) {
            return "newnew/orange-front.png";
        } else if (i == 4 || i == 5) {
            return "newnew/blue-front.png";
        } else if (i == 6 || i == 7) {
            return "newnew/pink-front.png";
        } else {
            return "newnew/purple-front.png";
        }
    }

    public static String defineBack(int i) {
        if (i == 0 || i == 1) {
            return "newnew/green-back.png";
        } else if (i == 2 || i == 3) {
            return "newnew/orange-back.png";
        } else if (i == 4 || i == 5) {
            return "newnew/blue-back.png";
        } else if (i == 6 || i == 7) {
            return "newnew/pink-back.png";
        } else {
            return "newnew/purple-back.png";
        }
    }
}
