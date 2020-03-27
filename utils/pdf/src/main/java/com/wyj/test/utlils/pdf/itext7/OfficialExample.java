package com.wyj.test.utlils.pdf.itext7;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author: wuyingjie
 * @date: 2020-01-17
 */
public class OfficialExample {

    public static class SimpleRowColspan {
        public static final String DEST = "/Users/wuyingjie/Desktop/simple_row_colspan.pdf";

        public static void main(String[] args) throws Exception {
            File file = new File(DEST);
            file.getParentFile().mkdirs();
            new SimpleRowColspan().manipulatePdf(DEST);
        }

        protected void manipulatePdf(String dest) throws Exception {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
            Document doc = new Document(pdfDoc);
//            doc.setFont(PdfFontFactory.createFont());
            PdfFont cjk = PdfFontFactory.createFont("/Users/wuyingjie/wyj/test-parent/utils/pdf/src/main/resources/NotoSansCJKsc-Regular.otf", PdfEncodings.IDENTITY_H, true);
            doc.setFont(cjk);
            FileInputStream fis = new FileInputStream("/Users/wuyingjie/wyj/test-parent/utils/pdf/src/main/resources/NotoSansCJKsc-Regular.otf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int length = 0;
            while ((length = fis.read(data)) > 0) {
                baos.write(data, 0, length);
            }
            PdfFont cjk2 = PdfFontFactory.createFont(baos.toByteArray(), PdfEncodings.IDENTITY_H, true);
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            fis.close();
            fis = new FileInputStream("/Users/wuyingjie/wyj/test-parent/utils/pdf/src/main/resources/NotoSansCJKsc-Regular.otf");
            while ((length = fis.read(data)) > 0) {
                baos2.write(data, 0, length);
            }
            PdfFont cjk3 = PdfFontFactory.createFont(baos2.toByteArray(), PdfEncodings.IDENTITY_H, true);
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 1}));

            Cell cell;
            cell = new Cell(2, 1).add(new Paragraph("S/N"));
            table.addCell(cell);
            cell = new Cell(1, 3).add(new Paragraph("Name"));
            table.addCell(cell);
            cell = new Cell(2, 1).add(new Paragraph("Age"));
            table.addCell(cell);
            table.addCell("SURNAME");
            table.addCell("FIRST NAME");
            table.addCell("MIDDLE NAME");
            table.addCell("1");
            table.addCell("James");
            table.addCell("Fish");
            table.addCell("Stone");
            table.addCell("武英杰");
            doc.add(table);

            doc.close();
        }
    }

    public static class ColspanRowspan {
        public static final String DEST = "/Users/wuyingjie/Desktop/colspan_rowspan.pdf";

        public static void main(String[] args) throws Exception {
            File file = new File(DEST);
            file.getParentFile().mkdirs();
            new ColspanRowspan().manipulatePdf(DEST);
        }

        protected void manipulatePdf(String dest) throws Exception {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
            Document doc = new Document(pdfDoc);

            Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
            Cell cell = new Cell().add(new Paragraph(" 1,1 "));
            table.addCell(cell);
            cell = new Cell().add(new Paragraph(" 1,2 "));
            table.addCell(cell);
            Cell cell23 = new Cell(2, 2).add(new Paragraph("multi 1,3 and 1,4"));
            cell.setBold();
            table.addCell(cell23);
            cell = new Cell().add(new Paragraph(" 2,1 "));
            table.addCell(cell);
            cell = new Cell().add(new Paragraph(" 2,2 "));
            cell.setBold();
            table.addCell(cell);


            cell = new Cell(2, 4);
            Paragraph big = new Paragraph("2,4");
            big.setTextAlignment(TextAlignment.CENTER);
//            big.setFixedLeading(0);
//            big.setMultipliedLeading(1);
            cell.setMinHeight(200);
            cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell.setBold();
            cell.add(big);

            table.addCell(cell);


            doc.add(table);

            doc.close();
        }
    }
}
