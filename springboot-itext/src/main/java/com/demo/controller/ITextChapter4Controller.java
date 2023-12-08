package com.demo.controller;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfChoiceFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Link;
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
import java.util.Map;

/**
 * <p> @Title ITextChapter4Controller
 * <p> @Description iText Chapter-4测试Controller
 *
 * @author zhj
 * @date 2023/4/4 11:36
 */
@RestController
@RequestMapping("/chapter4")
public class ITextChapter4Controller {

    /**
     * 创建PDF，添加文本批注
     */
    @GetMapping(value = "/textAnnotation", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> textAnnotation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        //Initialize document
        Document document = new Document(pdf);
        document.add(new Paragraph("The example of text annotation."));

        //Create text annotation
        PdfAnnotation ann = new PdfTextAnnotation(new Rectangle(20, 800, 0, 0))
                .setOpen(true)
                .setColor(ColorConstants.GREEN)
                .setTitle(new PdfString("iText"))
                .setContents("With iText, you can truly take your documentation needs to the next level.");
        pdf.getFirstPage().addAnnotation(ann);
        document.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 创建PDF，添加链接批注
     */
    @GetMapping(value = "/linkAnnotation", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> linkAnnotation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        //Initialize document
        Document document = new Document(pdf);

        //Create link annotation
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(new Rectangle(0, 0))
                .setAction(PdfAction.createURI("http://itextpdf.com/"));
        Link link = new Link("here", annotation);
        Paragraph p = new Paragraph("The example of link annotation. Click ")
                .add(link.setUnderline())
                .add(" to learn more...");
        document.add(p);

        //Close document
        document.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 创建PDF，添加线批注
     */
    @GetMapping(value = "/lineAnnotation", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> lineAnnotation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        PdfPage page = pdf.addNewPage();

        PdfArray lineEndings = new PdfArray();
        lineEndings.add(new PdfName("Diamond"));
        lineEndings.add(new PdfName("Diamond"));

        //Create line annotation with inside caption
        PdfAnnotation annotation = new PdfLineAnnotation(
                new Rectangle(0, 0),
                new float[]{20, 790, page.getPageSize().getWidth() - 20, 790})
                .setLineEndingStyles((lineEndings))
                .setContentsAsCaption(true)
                .setTitle(new PdfString("iText"))
                .setContents("The example of line annotation")
                .setColor(ColorConstants.BLUE);
        page.addAnnotation(annotation);

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

    /**
     * 创建PDF，添加文本标记批注
     */
    @GetMapping(value = "/textMarkupAnnotation", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> textMarkupAnnotation() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));

        //Initialize document
        Document document = new Document(pdf);

        Paragraph p = new Paragraph("The example of text markup annotation.");
        document.showTextAligned(p, 20, 795, 1, TextAlignment.LEFT,
                VerticalAlignment.MIDDLE, 0);

        //Create text markup annotation
        PdfAnnotation ann = PdfTextMarkupAnnotation.createHighLight(new Rectangle(105, 790, 64, 10),
                        new float[]{169, 790, 105, 790, 169, 800, 105, 800})
                .setColor(ColorConstants.YELLOW)
                .setTitle(new PdfString("Hello!"))
                .setContents(new PdfString("I'm a popup."))
                .setTitle(new PdfString("iText"))
                .setRectangle(new PdfArray(new float[]{100, 600, 200, 100}));
        pdf.getFirstPage().addAnnotation(ann);

        //Close document
        document.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 创建PDF，添加简单组件的批注（职位申请表）
     */
    @GetMapping(value = "/jobApplication", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> jobApplication() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        PageSize ps = PageSize.A4;
        pdf.setDefaultPageSize(ps);

        // Initialize document
        Document document = new Document(pdf);

        addAcroForm(document);

        //Close document
        document.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * Add acroform to the document
     * @param doc   document
     * @return      PdfAcroForm
     */
    private PdfAcroForm addAcroForm(Document doc) {

        Paragraph title = new Paragraph("Application for employment")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        doc.add(title);
        doc.add(new Paragraph("Full name:").setFontSize(12));
        doc.add(new Paragraph("Native language:      English         French       German        Russian        Spanish").setFontSize(12));
        doc.add(new Paragraph("Experience in:       cooking        driving           software development").setFontSize(12));
        doc.add(new Paragraph("Preferred working shift:").setFontSize(12));
        doc.add(new Paragraph("Additional information:").setFontSize(12));

        //Add acroform
        PdfAcroForm form = PdfAcroForm.getAcroForm(doc.getPdfDocument(), true);

        //Create text field
        PdfTextFormField nameField = PdfTextFormField.createText(doc.getPdfDocument(),
                new Rectangle(99, 753, 425, 15), "name", "");
        form.addField(nameField);

        //Create radio buttons
        PdfButtonFormField group = PdfFormField.createRadioGroup(doc.getPdfDocument(), "language", "");
        PdfFormField.createRadioButton(doc.getPdfDocument(), new Rectangle(130, 728, 15, 15), group, "English");
        PdfFormField.createRadioButton(doc.getPdfDocument(), new Rectangle(200, 728, 15, 15), group, "French");
        PdfFormField.createRadioButton(doc.getPdfDocument(), new Rectangle(260, 728, 15, 15), group, "German");
        PdfFormField.createRadioButton(doc.getPdfDocument(), new Rectangle(330, 728, 15, 15), group, "Russian");
        PdfFormField.createRadioButton(doc.getPdfDocument(), new Rectangle(400, 728, 15, 15), group, "Spanish");
        form.addField(group);

        //Create checkboxes
        for (int i = 0; i < 3; i++) {
            PdfButtonFormField checkField = PdfFormField.createCheckBox(doc.getPdfDocument(), new Rectangle(119 + i * 69, 701, 15, 15),
                    "experience".concat(String.valueOf(i+1)), "Off", PdfFormField.TYPE_CHECK);
            form.addField(checkField);
        }

        //Create combobox
        String[] options = {"Any", "6.30 am - 2.30 pm", "1.30 pm - 9.30 pm"};
        PdfChoiceFormField choiceField = PdfFormField.createComboBox(doc.getPdfDocument(), new Rectangle(163, 676, 115, 15),
                "shift", "Any", options);
        form.addField(choiceField);

        //Create multiline text field
        PdfTextFormField infoField = PdfTextFormField.createMultilineText(doc.getPdfDocument(),
                new Rectangle(158, 625, 366, 40), "info", "");
        form.addField(infoField);

        //Create push button field
        PdfButtonFormField button = PdfFormField.createPushButton(doc.getPdfDocument(),
                new Rectangle(479, 594, 45, 15), "reset", "RESET");
        button.setAction(PdfAction.createResetForm(new String[] {"name", "language", "experience1", "experience2", "experience3", "shift", "info"}, 0));
        form.addField(button);

        return form;

    }

    /**
     * 创建PDF，添加并填充表单（职位申请表）
     */
    @GetMapping(value = "/createAndFill", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createAndFill() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));

        // Initialize document
        Document doc = new Document(pdf);

        PdfAcroForm form = addAcroForm(doc);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("name").setValue("James Bond");
        fields.get("language").setValue("English");
        fields.get("experience1").setValue("Off");
        fields.get("experience2").setValue("Yes");
        fields.get("experience3").setValue("Yes");
        fields.get("shift").setValue("Any");
        fields.get("info").setValue("I was 38 years old when I became an MI6 agent.");

        doc.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 指定PDF，简单填写表单（职位申请表）
     */
    @GetMapping(value = "/flattenForm", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> flattenForm() throws IOException {
        final String src = "src/main/resources/pdf/job_application.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfReader(src), new PdfWriter(outputStream));

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("name").setValue("James Bond");
        fields.get("language").setValue("English");
        fields.get("experience1").setValue("Off");
        fields.get("experience2").setValue("Yes");
        fields.get("experience3").setValue("Yes");
        fields.get("shift").setValue("Any");
        fields.get("info").setValue("I was 38 years old when I became an MI6 agent.");
        form.flattenFields();

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
