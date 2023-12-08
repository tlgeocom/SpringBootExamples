package com.demo.controller;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
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
import java.util.Map;

/**
 * <p> @Title ITextChapter5Controller
 * <p> @Description iText Chapter-5测试Controller
 *
 * @author zhj
 * @date 2023/4/5 19:43
 */
@RestController
@RequestMapping("/chapter5")
public class ITextChapter5Controller {

    /**
     * 创建PDF，添加可回复型批注
     */
    @GetMapping(value = "/addAnnotationAndContent", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> addAnnotationAndContent() throws IOException {
        final String src = "src/main/resources/pdf/job_application.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(outputStream));

        //Add text annotation
        PdfAnnotation ann = new PdfTextAnnotation(new Rectangle(400, 795, 0, 0))
                .setOpen(true)
                .setTitle(new PdfString("iText"))
                .setContents("Please, fill out the form.");
        pdfDoc.getFirstPage().addAnnotation(ann);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.beginText().setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 12)
                .moveText(265, 597)
                .showText("I agree to the terms and conditions.")
                .endText();

        //Add form field
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfButtonFormField checkField = PdfFormField.createCheckBox(pdfDoc, new Rectangle(245, 594, 15, 15),
                "agreement", "Off", PdfFormField.TYPE_CHECK);
        checkField.setRequired(true);
        form.addField(checkField);

        //Update reset button
        form.getField("reset").setAction(PdfAction.createResetForm(new String[]{"name", "language",
                "experience1", "experience2", "experience3", "shift", "info", "agreement"}, 0));

        pdfDoc.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，填写并修改表单
     */
    @GetMapping(value = "/fillAndModifyForm", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> fillAndModifyForm() throws IOException {
        final String src = "src/main/resources/pdf/job_application.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(outputStream));


        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();

        fields.get("name").setValue("James Bond").setBackgroundColor(ColorConstants.ORANGE);
        fields.get("language").setValue("English");

        fields.get("experience1").setValue("Yes");
        fields.get("experience2").setValue("Yes");
        fields.get("experience3").setValue("Yes");

        List<PdfString> options = new ArrayList<PdfString>();
        options.add(new PdfString("Any"));
        options.add(new PdfString("8.30 am - 12.30 pm"));
        options.add(new PdfString("12.30 pm - 4.30 pm"));
        options.add(new PdfString("4.30 pm - 8.30 pm"));
        options.add(new PdfString("8.30 pm - 12.30 am"));
        options.add(new PdfString("12.30 am - 4.30 am"));
        options.add(new PdfString("4.30 am - 8.30 am"));
        PdfArray arr = new PdfArray(options);
        fields.get("shift").setOptions(arr);
        fields.get("shift").setValue("Any");

        PdfFont courier = PdfFontFactory.createFont(StandardFonts.COURIER);
        fields.get("info").setValue("I was 38 years old when I became an MI6 agent.", courier, 7f);

        pdfDoc.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，填充内容（页眉、页脚线、页脚页码、水印）
     */
    @GetMapping(value = "/addContent", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> addContent() throws IOException {
        final String src = "src/main/resources/pdf/ufo.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(outputStream));

        Document document = new Document(pdfDoc);
        Rectangle pageSize;
        PdfCanvas canvas;
        int n = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            PdfPage page = pdfDoc.getPage(i);
            pageSize = page.getPageSize();
            canvas = new PdfCanvas(page);
            // 画页眉文字
            canvas.beginText().setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 7)
                    .moveText(pageSize.getWidth() / 2 - 24, pageSize.getHeight() - 10)
                    .showText("I want to believe")
                    .endText();
            // 画页脚线
            canvas.setStrokeColor(ColorConstants.BLACK)
                    .setLineWidth(.2f)
                    .moveTo(pageSize.getWidth() / 2 - 30, 20)
                    .lineTo(pageSize.getWidth() / 2 + 30, 20).stroke();
            // 画页码
            canvas.beginText().setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 7)
                    .moveText(pageSize.getWidth() / 2 - 7, 10)
                    .showText(String.valueOf(i))
                    .showText(" of ")
                    .showText(String.valueOf(n))
                    .endText();
            // 画水印
            Paragraph p = new Paragraph("CONFIDENTIAL").setFontSize(60);
            canvas.saveState();
            PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.2f);
            canvas.setExtGState(gs1);
            document.showTextAligned(p,
                    pageSize.getWidth() / 2, pageSize.getHeight() / 2,
                    pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
            canvas.restoreState();
        }

        pdfDoc.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，改变页面（大小、边框、旋转）
     */
    @GetMapping(value = "/changePage", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> changePage() throws IOException {
        final String src = "src/main/resources/pdf/ufo.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(outputStream));


        float margin = 72;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i);
            // change page size
            Rectangle mediaBox = page.getMediaBox();
            Rectangle newMediaBox = new Rectangle(mediaBox.getLeft() - margin, mediaBox.getBottom() - margin,
                    mediaBox.getWidth() + margin * 2, mediaBox.getHeight() + margin * 2);
            page.setMediaBox(newMediaBox);
            // add border
            PdfCanvas over = new PdfCanvas(page);
            over.setStrokeColor(ColorConstants.GRAY);
            over.rectangle(mediaBox.getLeft(), mediaBox.getBottom(), mediaBox.getWidth(), mediaBox.getHeight());
            over.stroke();
            // change rotation of the even pages
            if (i % 2 == 0) {
                page.setRotation(180);
            }
        }

        pdfDoc.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }
}
