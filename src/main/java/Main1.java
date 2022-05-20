
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main1 {
    private static final int COORDINATE_Y = 706;
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File("example.pdf");
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        writer.setPageEmpty(false);
        document.open();
        int coordinateY = 660;
        int j = 0;
        for (int i = 0; i < 20; i++) {

            String path = i % 2 == 0 ? "blank/invalid1.jpg" : "blank/pensioner.jpg";
            Image image = Image.getInstance(path);
            image.scaleAbsolute(595, 161.5f);

            image.setAbsolutePosition(0, coordinateY - 161.5f*j);
            document.add(image);
            System.out.println(coordinateY - 161.5f*j);
            if((j + 1) % 5 == 0) {
                j = 0;
                document.newPage();
            } else {
                j++;
            }

        }

        document.close();
        setImage();
    }

    public static void setImage() throws IOException, DocumentException {
        String path = "example.pdf";
        PdfReader pdfReader = new PdfReader(path);
        PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream("newpdf.pdf"));


        int coordinateY = COORDINATE_Y;
        int j = 0;
        int page = 1;
        for(int i = 0; i < 20; i++) {
            PdfContentByte content = stamper.getOverContent(page);
            System.out.println(content);
            addImage(content, 193,coordinateY - 161.9f*j);
            setText("Rakhmet Rakhmetov", 105, coordinateY - 22 - 161.9f*j, content);
            setText("123456789123", 230, coordinateY - 22 - 161.9f*j, content);
            setText("123456456", 350, coordinateY + 80 - 161.9f*j, content);
            setBarCode("123456456", 500, coordinateY + 74 - 161.9f*j, content);
            if((j + 1) % 5 == 0) {
                j = 0;
                 page++;
            } else {
                j++;
            }
        }
        stamper.close();
    }

    public static void addImage(PdfContentByte content, float x, float y) throws IOException, DocumentException {
        Image image = Image.getInstance("blank/W-95.jpg");
        image.scaleAbsolute(77, 100);
        image.setAbsolutePosition(x, y);
        content.addImage(image);
    }

    private static void setText(String text, float x, float y, PdfContentByte content) throws IOException, DocumentException {
        BaseFont bfComic = BaseFont.createFont("Calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        content.setFontAndSize(bfComic, 10);
        content.beginText();
        content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 0f);
        content.endText();
        content.stroke();
    }

    private static void setBarCode(String barCode, float x, float y, PdfContentByte content) {
        Barcode128 code128 = new Barcode128();
        code128.setCode(barCode);
        code128.setCodeType(Barcode128.CODE128);
        code128.setSize(5);
        code128.setBarHeight(20);
        code128.setX(0.5f);
        code128.setFont(null);
        PdfTemplate template = code128.createTemplateWithBarcode(
                content, BaseColor.BLACK, BaseColor.BLACK);
        content.addTemplate(template, x, y);
    }
}
