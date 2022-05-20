import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;

import javax.imageio.ImageIO;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Yarmarka {
    static long bar = 1257321001L;
    private static final int COORDINATE_X = 193;
    private static final int COORDINATE_Y = 706;

    public static void main(String[] args) throws IOException, DocumentException {

        ArrayList<String> invalidList = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
            invalidList.addAll(createFile(1));

//        }
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

//    private static ArrayList<String> createFile(int j) throws IOException, DocumentException {
//        String resultPathFront = "result/" + j + "-out.pdf";
//        ArrayList<String> listPath = new ArrayList<>();
//
//        String resultPathBack = "result/" + j  + "-out.pdf";
//        String path = "blank/setka.pdf";
//        PdfReader pdfReader = new PdfReader(path);
//        PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(resultPathFront));
//        String path2 = "blank/setka.pdf";
//        PdfReader pdfReaderBack = new PdfReader(path2);
//        PdfStamper back = new PdfStamper(pdfReaderBack, new FileOutputStream(resultPathBack));
//
//        PdfContentByte backContent = back.getOverContent(1);
//        PdfContentByte content = stamper.getOverContent(1);
//        int coordinateY = COORDINATE_Y;
//        int moveY = 163;
//        int column = 1;
//
//        for (int i = 0; i < 10; i++) {
////                String blankFrontPath = "newnew/front.png";
//            String blankBackPath = "newnew/1newyarmarka.png";
////            String blankFrontPath = "new/yellow-front.png";
////            String blankBackPath = "new/yellow-back.png";
//            String barCode = Long.toString(bar);
//            bar++;
//            String editedBarCode = barCode.substring(0, 3) + " " + barCode.substring(3, 6) + " " + barCode.substring(6);
//            String iin = "980916301576";
//
//            String editedIin = iin.substring(0, 4) + " " + iin.substring(4, 8) + " " + iin.substring(8);
//            if (column == 1) {
////                    setImage(blankFrontPath, COORDINATE_X - 159, coordinateY - 40, content);
//                setImage(blankBackPath, COORDINATE_X * 2 + 86 - 160, coordinateY - 40, backContent);
////                setPhoto("W-12.jpg", content, COORDINATE_X, coordinateY);
////                setText("Антон Павлович", COORDINATE_X - 80, coordinateY - 18, content, 10);
////                setText(editedIin,COORDINATE_X + 33, coordinateY - 18, content, 10);
////
//                setBarCode(barCode, COORDINATE_X*2 + 75, coordinateY, backContent);
//                setText(editedBarCode, COORDINATE_X * 2 - 1, coordinateY + 84, backContent, 16);
////                createRectangle(COORDINATE_X + 120, coordinateY + 65, backContent);
//
////                setQRCode(barCode, COORDINATE_X * 2 + 86 + 4 + 40, coordinateY - 37.5f, backContent);
////                setText(editedBarCode, COORDINATE_X * 2 + 110 + 2, coordinateY + 43, backContent, 16);
//                column++;
//
//            } else {
////                    setImage(blankFrontPath, COORDINATE_X * 2 + 86 - 160, coordinateY - 40, content);
//                setImage(blankBackPath, COORDINATE_X - 159, coordinateY - 40, backContent);
//
////                setPhoto("W-12.jpg", content,COORDINATE_X * 2 + 86, coordinateY);
////                setText("Антон Павлович", COORDINATE_X * 2 + 6, coordinateY - 18, content, 10);
////                setText(editedIin, COORDINATE_X*2 + 119, coordinateY - 18, content, 10);
////
//                setBarCode(barCode, COORDINATE_X - 6, coordinateY, backContent);
//                setText(editedBarCode, COORDINATE_X - 90, coordinateY + 84, backContent, 16);
////                createRectangle(COORDINATE_X, coordinateY + 70, content);
////                setQRCode(barCode, COORDINATE_X + 8 + 37, coordinateY - 37.5f, backContent);
////                setText(editedBarCode, COORDINATE_X + 28, coordinateY + 43, backContent, 16);
//                column = 1;
//                coordinateY -= moveY;
//            }
////         }
//
//
//
//        }
//        listPath.add(resultPathBack);
//        back.close();
////            stamper.close();
//        return  listPath;
//    }

    private static ArrayList<String> createFile(int j) throws IOException, DocumentException {
        String resultPathFront = "result/" + j + "-out.pdf";
        ArrayList<String> listPath = new ArrayList<>();

        String resultPathBack = "result/" + j  + "-out.pdf";
        String path = "blank/setka.pdf";
        PdfReader pdfReader = new PdfReader(path);
        PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(resultPathFront));
        String path2 = "blank/setka.pdf";
        PdfReader pdfReaderBack = new PdfReader(path2);
        PdfStamper back = new PdfStamper(pdfReaderBack, new FileOutputStream(resultPathBack));

        PdfContentByte backContent = back.getOverContent(1);
        PdfContentByte content = stamper.getOverContent(1);
        int coordinateY = COORDINATE_Y;
        int moveY = 163;
        int column = 1;

        for (int i = 0; i < 10; i++) {
                String blankFrontPath = "newnew/brown-new-back.png";
            String blankBackPath = "newnew/brown-new-back.png";
//            String blankFrontPath = "new/yellow-front.png";
//            String blankBackPath = "new/yellow-back.png";
            String barCode = Long.toString(bar);
            bar++;
            String editedBarCode = barCode.substring(0, 3) + " " + barCode.substring(3, 6) + " " + barCode.substring(6);
            String iin = "980916301576";

            String editedIin = iin.substring(0, 4) + " " + iin.substring(4, 8) + " " + iin.substring(8);
            if (column == 1) {
                    setImage(blankFrontPath, COORDINATE_X - 159, coordinateY - 40, content);
                setImage(blankBackPath, COORDINATE_X * 2 + 86 - 160, coordinateY - 40, backContent);
//                setPhoto("W-12.jpg", content, COORDINATE_X, coordinateY);
//                setText("Антон Павлович", COORDINATE_X - 80, coordinateY - 18, content, 10);
//                setText(editedIin,COORDINATE_X + 33, coordinateY - 18, content, 10);
//
//                setBarCode(barCode, COORDINATE_X*2 + 75, coordinateY, backContent);
//                setText(editedBarCode, COORDINATE_X * 2 - 1, coordinateY + 84, backContent, 16);
//                createRectangle(COORDINATE_X + 120, coordinateY + 65, backContent);

                setQRCode(barCode, COORDINATE_X * 2 + 86 + 4 + 40, coordinateY - 37.5f, backContent);
                setText(editedBarCode, COORDINATE_X * 2 + 110 + 2, coordinateY + 43, backContent, 16);
                column++;

            } else {
                    setImage(blankFrontPath, COORDINATE_X * 2 + 86 - 160, coordinateY - 40, content);
                setImage(blankBackPath, COORDINATE_X - 159, coordinateY - 40, backContent);

//                setPhoto("W-12.jpg", content,COORDINATE_X * 2 + 86, coordinateY);
//                setText("Антон Павлович", COORDINATE_X * 2 + 6, coordinateY - 18, content, 10);
//                setText(editedIin, COORDINATE_X*2 + 119, coordinateY - 18, content, 10);
//
//                setBarCode(barCode, COORDINATE_X - 6, coordinateY, backContent);
//                setText(editedBarCode, COORDINATE_X - 90, coordinateY + 84, backContent, 16);
//                createRectangle(COORDINATE_X, coordinateY + 70, content);
                setQRCode(barCode, COORDINATE_X + 8 + 37, coordinateY - 37.5f, backContent);
                setText(editedBarCode, COORDINATE_X + 28, coordinateY + 43, backContent, 16);
                column = 1;
                coordinateY -= moveY;
            }
//         }



        }
        listPath.add(resultPathBack);
        back.close();
//            stamper.close();
        return  listPath;
    }

    private static void setText(String text, float x, float y, PdfContentByte content, int fontSize) throws IOException, DocumentException {
        BaseFont bfComic = BaseFont.createFont("Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        content.setColorFill(BaseColor.BLACK);
        content.setFontAndSize(bfComic, fontSize);
        content.beginText();
        content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 0f);
//        content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 270f);
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
        codeQrImage.setAbsolutePosition(x,y);
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
//        String imagePath = "C:\\path\\to\\image.jpg";
        java.awt.Image awtImage = ImageIO.read(new File(path));
// scale image here
        int scaledWidth = awtImage.getWidth(null);
        int scaledHeight = awtImage.getHeight(null);
        BufferedImage scaledAwtImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledAwtImage.createGraphics();
        g.drawImage(awtImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

/*
Optionally pick a color to replace with transparency.
Any pixels that match this color will be replaced by tansparency.
*/
        Color bgColor = Color.WHITE;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledAwtImage, "png", baos);
        Image image = Image.getInstance(baos.toByteArray());

//        Image image = Image.getInstance(path);
        image.scaleAbsoluteHeight(162);
        image.scaleAbsoluteWidth(254);
        image.setAbsolutePosition(x, y);
        content.addImage(image);

    }

    private static void createRectangle(int x, int y, PdfContentByte contentByte) {
        contentByte.rectangle(x, y, 250, 40);
        contentByte.setColorFill(BaseColor.WHITE);
        contentByte.fill();
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
        if(i == 0 || i == 1) {
            return "newnew/green-front.png";
        } else if(i == 2 || i == 3) {
            return "newnew/orange-front.png";
        } else if(i == 4 || i == 5) {
            return "newnew/blue-front.png";
        } else if(i == 6 || i == 7) {
            return "newnew/pink-front.png";
        } else  {
            return "newnew/purple-front.png";
        }
    }

    public static String defineBack(int i) {
        if(i == 0 || i == 1) {
            return "newnew/green-back.png";
        } else if(i == 2 || i == 3) {
            return "newnew/orange-back.png";
        } else if(i == 4 || i == 5) {
            return "newnew/blue-back.png";
        } else if(i == 6 || i == 7) {
            return "newnew/pink-back.png";
        } else  {
            return "newnew/purple-back.png";
        }
    }

}
