//package com.wyj.test.utlils.pdf.itext7;
//
//import com.itextpdf.io.font.PdfEncodings;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.property.HorizontalAlignment;
//import com.itextpdf.layout.property.TextAlignment;
//import com.itextpdf.layout.property.UnitValue;
//import com.itextpdf.layout.property.VerticalAlignment;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.List;
//
///**
// * @author: wuyingjie
// * @date: 2020-01-13
// */
//public class PdfExcelUtils {
//
//    public static final String DEST = "/Users/wuyingjie/Desktop/wyj_test_2.pdf";
//
//    public static void main(String[] args) {
//
//    }
//
//    /**
//     * UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 9, 3, 3, 3, 2})
//     * UnitValue.createPercentArray(columns)
//     */
//    public static void generatorExcelPdf(OutputStream os, List<ITextCellBuilderVO> pdfCellList, UnitValue[] columnWidths, float fontSize) throws IOException {
//
//        int columns = columnWidths.length;
//        long now = System.currentTimeMillis();
//        PdfFont cjk = PdfFontFactory.createFont(System.getProperty("user.dir")+"/receipt/苹方黑体-准-简.ttf", PdfEncodings.IDENTITY_H, true);
//
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(os));
//        Document doc = new Document(pdfDoc);
//        doc.setFont(cjk);
//        doc.setFontSize(fontSize);
//
//        Table table = new Table(columnWidths);
//        addCell(table, columns, pdfCellList);
//        doc.add(table);
//        System.out.println("excel pdf 用时:"+(System.currentTimeMillis() - now));
//        doc.close();
//    }
//
//    public static void generatorExcelPdf(OutputStream os, List<ITextCellBuilderVO> pdfCellList, UnitValue[] columnWidths) throws IOException {
//        generatorExcelPdf(os, pdfCellList, columnWidths, 10);
//    }
//
//    public static void generatorExcelPdf(OutputStream os, List<ITextCellBuilderVO> pdfCellList, int columns) throws IOException {
//        generatorExcelPdf(os, pdfCellList, UnitValue.createPercentArray(columns), 10);
//    }
//
//
//
//    private static void addCell(Table table, int columns, List<ITextCellBuilderVO> cells) {
//        int all = cells.stream().map(item -> item.x*item.y)
//                .reduce(0, (Integer sum, Integer item) -> {
//                    sum += item;
//                    return sum;
//                });
//
//        if (all % columns != 0) {
//            throw new RuntimeException("必须整列的插入");
//        }
//
//        for (ITextCellBuilderVO item : cells) {
//            Cell cell = new Cell(item.x, item.y);
//            Paragraph para = new Paragraph(item.text);
//
//
//            if (item.isCenter) {
//                para.setTextAlignment(TextAlignment.CENTER);
//            }
//            cell.add(para);
//
//            if (item.isCenter) {
//                cell.setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER);
//            }
//            if (item.isBold) {
//                cell.setBold();
//            }
//            table.addCell(cell);
//        }
//    }
//
//    public static class ITextCellBuilderVO {
//        int x,y;
//        String text;
//        boolean isCenter = false;
//        boolean isBold = false;
//
//        /**
//         *
//         * @param x colspan 占用的列数
//         * @param y rowspan 占用的行数
//         * @param text 内容
//         */
//        public ITextCellBuilderVO(int x, int y, String text) {
//            this.x = x;
//            this.y = y;
//            this.text = text;
//        }
//
//        public ITextCellBuilderVO inCenter() {
//            isCenter = true;
//            return this;
//        }
//
//        public ITextCellBuilderVO bold() {
//            isBold = true;
//            return this;
//        }
//    }
//}
