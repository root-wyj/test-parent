//package com.wyj.test.utlils.pdf.itext7;
//
//
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.property.UnitValue;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author: wuyingjie
// * @date: 2020-01-09
// */
//public class PdfWyjTest {
//    public static final String DEST = "/Users/wuyingjie/Desktop/wyj_test.pdf";
//
//    // https://itextpdf.com/en/resources/examples/itext-7/fonts
//    // https://itextpdf.com/en/resources/examples/itext-7/colspan-and-rowspan
//    public static void main(String[] args) throws IOException {
//
//        PdfFont cjk = PdfFontFactory.createFont(System.getProperty("user.dir")+"/receipt/苹方黑体-准-简.ttf", "Identity-H", true);
//        PdfFont font = PdfFontFactory.createFont();
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
//        Document doc = new Document(pdfDoc);
//        doc.setFont(cjk);
//        doc.setFontSize(10);
//
//        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 9, 3, 3, 3, 2}));
////        Table table = new Table(UnitValue.createPercentArray(9)).useAllAvailableWidth();
//        List<ITextCellBuilderVO> list = new ArrayList<>();
//
//        // 第一部分
//        list.add(new ITextCellBuilderVO(1,9, "服务器及配件采购订单"));
//
//        list.add(new ITextCellBuilderVO(1, 2, "系统编号"));
//        list.add(new ITextCellBuilderVO(1, 7, "$applyNo"));
//
//        list.add(new ITextCellBuilderVO(1, 2, "主合同编号"));
//        list.add(new ITextCellBuilderVO(1, 3, "$contractCode"));
//        list.add(new ITextCellBuilderVO(1, 1, "订单编号"));
//        list.add(new ITextCellBuilderVO(1, 3, "$合同文本好"));
//
//        list.add(new ITextCellBuilderVO(1, 2, "下单时间"));
//        list.add(new ITextCellBuilderVO(1, 7, "$createTime"));
//
//        list.add(new ITextCellBuilderVO(1, 2, "交货地点"));
//        list.add(new ITextCellBuilderVO(1, 7, "发货前通知"));
//
//        list.add(new ITextCellBuilderVO(1, 2, "交货时间"));
//        list.add(new ITextCellBuilderVO(1, 7, "$demandTime"));
//
//        list.add(new ITextCellBuilderVO(1, 3, "乙方（供货方）"));
//        list.add(new ITextCellBuilderVO(1, 6, "$supplierName"));
//
//
//        list.add(new ITextCellBuilderVO(2, 2, "联系人"));
//        list.add(new ITextCellBuilderVO(2, 3, "$contactName"));
//
//        list.add(new ITextCellBuilderVO(1, 1, "电话"));
//        list.add(new ITextCellBuilderVO(1, 3, "$phone"));
//
//        list.add(new ITextCellBuilderVO(1, 1, "邮箱"));
//        list.add(new ITextCellBuilderVO(1, 3, "$email"));
//
//        // 甲方
//        list.add(new ITextCellBuilderVO(1, 3, "甲方（收货方）"));
//        list.add(new ITextCellBuilderVO(1, 6, "$companyName"));
//
//
//        list.add(new ITextCellBuilderVO(2, 2, "订单联系人"));
//        list.add(new ITextCellBuilderVO(2, 3, "$applyName"));
//
//        list.add(new ITextCellBuilderVO(1, 1, "电话"));
//        list.add(new ITextCellBuilderVO(1, 3, "$applyPhone"));
//
//        list.add(new ITextCellBuilderVO(1, 1, "邮箱"));
//        list.add(new ITextCellBuilderVO(1, 3, "$applyEmail"));
//
//        list.add(new ITextCellBuilderVO(4, 2, "签收人"));
//
//        list.add(new ITextCellBuilderVO(2, 3, "张乔吉"));
//        list.add(new ITextCellBuilderVO(1, 1, "电话"));
//        list.add(new ITextCellBuilderVO(1, 3, "18513622109"));
//        list.add(new ITextCellBuilderVO(1, 1, "邮箱"));
//        list.add(new ITextCellBuilderVO(1, 3, "zhangqiaoji@kuaishou.com"));
//
//        list.add(new ITextCellBuilderVO(2, 3, "金旭"));
//        list.add(new ITextCellBuilderVO(1, 1, "电话"));
//        list.add(new ITextCellBuilderVO(1, 3, "13810373858"));
//        list.add(new ITextCellBuilderVO(1, 1, "邮箱"));
//        list.add(new ITextCellBuilderVO(1, 3, "jinxu@kuaishou.com"));
//
//        addCell(table, 9, list);
//
//
//        // 第二部分
//        list.clear();
//        list.add(new ITextCellBuilderVO(1,9, "订单详情"));
//
//        // title
//        list.add(new ITextCellBuilderVO(2, 1, "No"));
//        list.add(new ITextCellBuilderVO(2, 1, "套餐型号"));
//        list.add(new ITextCellBuilderVO(2, 1, "型号"));
//        list.add(new ITextCellBuilderVO(2, 1, "配件"));
//        list.add(new ITextCellBuilderVO(2, 1, "技术规格"));
//        list.add(new ITextCellBuilderVO(2, 1, "本次需求量"));
//        list.add(new ITextCellBuilderVO(1, 1, "单价"));
//        list.add(new ITextCellBuilderVO(1, 1, "总价"));
//        list.add(new ITextCellBuilderVO(2, 1, "备注"));
//        list.add(new ITextCellBuilderVO(1, 1, "（人民币：元）"));
//        list.add(new ITextCellBuilderVO(1, 1, "（人民币：元）"));
//
//
//        // content
//
//        // tail
//        list.add(new ITextCellBuilderVO(1, 3, "设备数量总计（台）"));
//        list.add(new ITextCellBuilderVO(1, 2, ""));
//        list.add(new ITextCellBuilderVO(1, 1, "$sum"));
//        list.add(new ITextCellBuilderVO(1, 1, ""));
//        list.add(new ITextCellBuilderVO(1, 1, ""));
//        list.add(new ITextCellBuilderVO(1, 1, ""));
//
//        list.add(new ITextCellBuilderVO(1, 3, "金额总计（人民币：元）"));
//        list.add(new ITextCellBuilderVO(1, 2, "给人民币大写"));
//        list.add(new ITextCellBuilderVO(1, 1, ""));
//        list.add(new ITextCellBuilderVO(1, 1, ""));
//        list.add(new ITextCellBuilderVO(1, 1, "$sum"));
//        list.add(new ITextCellBuilderVO(1, 1, ""));
//
//        list.add(new ITextCellBuilderVO(1, 5, "甲方（收货方）："+"$jia"));
//        list.add(new ITextCellBuilderVO(1, 4, "乙方（供货方）："+"$yi"));
//        list.add(new ITextCellBuilderVO(1, 5, "授权签字人："));
//        list.add(new ITextCellBuilderVO(1, 4, "授权签字人："));
//        list.add(new ITextCellBuilderVO(1, 5, "日期："+"$date"));
//        list.add(new ITextCellBuilderVO(1, 4, "日期："+"$date"));
//
//        addCell(table, 9, list);
//
//        //
//        // end
//        doc.add(table);
//        doc.close();
//    }
//
//    private static void addCell(Table table, int columns, List<ITextCellBuilderVO> cells) {
//        int all = cells.stream().map(item -> item.x*item.y)
//                .reduce(0, (Integer sum, Integer item) -> {
//            sum += item;
//            return sum;
//        });
//
//        if (all % columns != 0) {
//            throw new RuntimeException("必须整列的插入");
//        }
//
//        for (ITextCellBuilderVO item : cells) {
//            table.addCell(new Cell(item.x, item.y).add(new Paragraph(item.text)));
//        }
//    }
//
//    private static class ITextCellBuilderVO {
//        int x,y;
//        String text;
//
//        ITextCellBuilderVO(int x, int y, String text) {
//            this.x = x;
//            this.y = y;
//            this.text = text;
//        }
//    }
//
//
//}
