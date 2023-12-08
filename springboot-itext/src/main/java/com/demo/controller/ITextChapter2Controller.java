package com.demo.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Title ITextChapter2Controller
 * <p> @Description iText Chapter-2测试Controller
 *
 * @author zhj
 * @date 2023/4/3 18:14
 */
@RestController
@RequestMapping("/chapter2")
public class ITextChapter2Controller {

    /**
     * 创建PDF，画坐标轴
     */
    @GetMapping(value = "/createPdfWithAxes", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithLines() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        PageSize ps = PageSize.A4.rotate();
        PdfPage page = pdf.addNewPage(ps);

        PdfCanvas canvas = new PdfCanvas(page);
        // 把坐标系的原点放到页面中间
        canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);
        drawAxes(canvas, ps);
        pdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 画坐标轴
     * @param canvas    画布
     * @param ps        页面大小
     */
    private void drawAxes(PdfCanvas canvas, PageSize ps) {
        // 画X轴
        canvas.moveTo(-(ps.getWidth() / 2 - 15), 0)
                .lineTo(ps.getWidth() / 2 - 15, 0)
                .stroke();

        // 画X轴箭头
        canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
                .moveTo(ps.getWidth() / 2 - 25, -10)
                .lineTo(ps.getWidth() / 2 - 15, 0)
                .lineTo(ps.getWidth() / 2 - 25, 10).stroke()
                .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.MITER);

        // 画y轴
        canvas.moveTo(0, -(ps.getHeight() / 2 - 15))
                .lineTo(0, ps.getHeight() / 2 - 15)
                .stroke();

        // 画Y轴箭头
        canvas.saveState()
                .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
                .moveTo(-10, ps.getHeight() / 2 - 25)
                .lineTo(0, ps.getHeight() / 2 - 15)
                .lineTo(10, ps.getHeight() / 2 - 25).stroke()
                .restoreState();

        // 画X衬线
        for (int i = -((int) ps.getWidth() / 2 - 61); i < ((int) ps.getWidth() / 2 - 60); i += 40) {
            canvas.moveTo(i, 5).lineTo(i, -5);
        }
        // 画Y衬线
        for (int j = -((int) ps.getHeight() / 2 - 57); j < ((int) ps.getHeight() / 2 - 56); j += 40) {
            canvas.moveTo(5, j).lineTo(-5, j);
        }
        canvas.stroke();
    }


    /**
     * 创建PDF，画网格线
     */
    @GetMapping(value = "/createPdfWithGridlines", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithGridlines(String dest) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        PageSize ps = PageSize.A4.rotate();
        PdfPage page = pdf.addNewPage(ps);

        PdfCanvas canvas = new PdfCanvas(page);
        // 把坐标系的原点放到页面中间
        canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);

        Color grayColor = new DeviceCmyk(0.f, 0.f, 0.f, 0.875f);
        Color greenColor = new DeviceCmyk(1.f, 0.f, 1.f, 0.176f);
        Color blueColor = new DeviceCmyk(1.f, 0.156f, 0.f, 0.118f);

        canvas.setLineWidth(0.5f).setStrokeColor(blueColor);

        // 画水平网格线
        for (int i = -((int) ps.getHeight() / 2 - 57); i < ((int) ps.getHeight() / 2 - 56); i += 40) {
            canvas.moveTo(-(ps.getWidth() / 2 - 15), i)
                    .lineTo(ps.getWidth() / 2 - 15, i);
        }
        // 画垂直网格线
        for (int j = -((int) ps.getWidth() / 2 - 61); j < ((int) ps.getWidth() / 2 - 60); j += 40) {
            canvas.moveTo(j, -(ps.getHeight() / 2 - 15))
                    .lineTo(j, ps.getHeight() / 2 - 15);
        }
        canvas.stroke();

        // 画轴
        canvas.setLineWidth(3).setStrokeColor(grayColor);
        drawAxes(canvas, ps);

        // 画虚线
        canvas.setLineWidth(2).setStrokeColor(greenColor)
                .setLineDash(10, 10, 8)
                .moveTo(-(ps.getWidth() / 2 - 15), -(ps.getHeight() / 2 - 15))
                .lineTo(ps.getWidth() / 2 - 15, ps.getHeight() / 2 - 15).stroke();
        pdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 创建PDF，并写星球大战
     */
    @GetMapping(value = "/createPdfWithStarWars", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithStarWars() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        //Add new page
        PageSize ps = PageSize.A4;
        PdfPage page = pdf.addNewPage(ps);

        PdfCanvas canvas = new PdfCanvas(page);

        List<String> text = new ArrayList<>();
        text.add("         Episode V         ");
        text.add("  THE EMPIRE STRIKES BACK  ");
        text.add("It is a dark time for the");
        text.add("Rebellion. Although the Death");
        text.add("Star has been destroyed,");
        text.add("Imperial troops have driven the");
        text.add("Rebel forces from their hidden");
        text.add("base and pursued them across");
        text.add("the galaxy.");
        text.add("Evading the dreaded Imperial");
        text.add("Starfleet, a group of freedom");
        text.add("fighters led by Luke Skywalker");
        text.add("has established a new secret");
        text.add("base on the remote ice world");
        text.add("of Hoth...");

        //Replace the origin of the coordinate system to the top left corner
        canvas.concatMatrix(1, 0, 0, 1, 0, ps.getHeight());
        canvas.beginText()
                .setFontAndSize(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD), 14)
                .setLeading(14 * 1.2f)
                .moveText(70, -40);
        for (String s : text) {
            //Add text and move to the next line
            canvas.newlineShowText(s);
        }
        canvas.endText();
        pdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 创建PDF，并滚动写星球大战
     */
    @GetMapping(value = "/createPdfWithStarWarsCrawl", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithStarWarsCrawl() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        //Add new page
        PageSize ps = PageSize.A4;
        PdfPage page = pdf.addNewPage(ps);

        List<String> text = new ArrayList();
        text.add("            Episode V      ");
        text.add("    THE EMPIRE STRIKES BACK  ");
        text.add("It is a dark time for the");
        text.add("Rebellion. Although the Death");
        text.add("Star has been destroyed,");
        text.add("Imperial troops have driven the");
        text.add("Rebel forces from their hidden");
        text.add("base and pursued them across");
        text.add("the galaxy.");
        text.add("Evading the dreaded Imperial");
        text.add("Starfleet, a group of freedom");
        text.add("fighters led by Luke Skywalker");
        text.add("has established a new secret");
        text.add("base on the remote ice world");
        text.add("of Hoth...");

        int maxStringWidth = 0;
        for (String fragment : text) {
            if (fragment.length() > maxStringWidth) {
                maxStringWidth = fragment.length();
            }
        }

        PdfCanvas canvas = new PdfCanvas(page);

        //Set black background
        canvas.rectangle(0, 0, ps.getWidth(), ps.getHeight())
                .setColor(ColorConstants.BLACK, true)
                .fill();

        //Replace the origin of the coordinate system to the top left corner
        canvas.concatMatrix(1, 0, 0, 1, 0, ps.getHeight());
        Color yellowColor = new DeviceCmyk(0.f, 0.0537f, 0.769f, 0.051f);
        float lineHeight = 5;
        float yOffset = -40;
        canvas.beginText()
                .setFontAndSize(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD), 1)
                .setColor(yellowColor, true);
        for (int j = 0; j < text.size(); j++) {
            String line = text.get(j);
            float xOffset = ps.getWidth() / 2 - 45 - 8 * j;
            float fontSizeCoeff = 6 + j;
            float lineSpacing = (lineHeight + j) * j / 1.5f;
            int stringWidth = line.length();
            for (int i = 0; i < stringWidth; i++) {
                float angle = (maxStringWidth / 2 - i) / 2f;
                float charXOffset = (4 + (float) j / 2) * i;
                canvas.setTextMatrix(fontSizeCoeff, 0,
                                angle, fontSizeCoeff / 1.5f,
                                xOffset + charXOffset, yOffset - lineSpacing)
                        .showText(String.valueOf(line.charAt(i)));
            }
        }
        canvas.endText();

        //Close document
        pdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

}
