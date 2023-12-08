package com.demo.controller;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.pdfa.PdfADocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * <p> @Title ITextChapter7Controller
 * <p> @Description iText Chapter-7测试Controller
 *
 * @author zhj
 * @date 2023/4/5 21:31
 */
@RestController
@RequestMapping("/chapter7")
public class ITextChapter7Controller {

    /**
     * 创建PDF，PDF/UA标准（快棕狐）
     */
    @GetMapping(value = "/quickBrownFoxPDFUA", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> quickBrownFoxPDFUA() throws IOException {
        final String dog = "src/main/resources/img/dog.bmp";
        final String fox = "src/main/resources/img/fox.bmp";
        final String myFont = "src/main/resources/font/FreeSans.ttf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream, new WriterProperties().addUAXmpMetadata()));
        Document document = new Document(pdf);

        //Setting some required parameters
        pdf.setTagged();
        pdf.getCatalog().setLang(new PdfString("en-US"));
        pdf.getCatalog().setViewerPreferences(
                new PdfViewerPreferences().setDisplayDocTitle(true));
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("iText7 PDF/UA example");

        //Fonts need to be embedded
        PdfFont font = PdfFontFactory.createFont(myFont, PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        Paragraph p = new Paragraph();
        p.setFont(font);
        p.add(new Text("The quick brown "));
        Image foxImage = new Image(ImageDataFactory.create(fox));
        //PDF/UA: Set alt text
        foxImage.getAccessibilityProperties().setAlternateDescription("Fox");
        p.add(foxImage);
        p.add(" jumps over the lazy ");
        Image dogImage = new Image(ImageDataFactory.create(dog));
        //PDF/UA: Set alt text
        dogImage.getAccessibilityProperties().setAlternateDescription("Dog");
        p.add(dogImage);

        document.add(p);
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
     * 创建PDF，PDF/A-1a标准（快棕狐）
     */
    @GetMapping(value = "/quickBrownFoxPDFA_1a", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> quickBrownFoxPDFA_1a() throws IOException {
        final String dog = "src/main/resources/img/dog.bmp";
        final String fox = "src/main/resources/img/fox.bmp";
        final String myFont = "src/main/resources/font/FreeSans.ttf";
        final String intent = "src/main/resources/color/sRGB_CS_profile.icm";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDFA document with output intent
        PdfADocument pdf = new PdfADocument(new PdfWriter(outputStream),
                PdfAConformanceLevel.PDF_A_1A,
                new PdfOutputIntent("Custom", "", "http://www.color.org",
                        "sRGB IEC61966-2.1", new FileInputStream(intent)));
        Document document = new Document(pdf);

        //Setting some required parameters
        pdf.setTagged();

        //Fonts need to be embedded
        PdfFont font = PdfFontFactory.createFont(myFont, PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        Paragraph p = new Paragraph();
        p.setFont(font);
        p.add(new Text("The quick brown "));
        Image foxImage = new Image(ImageDataFactory.create(fox));
        //Set alt text
        foxImage.getAccessibilityProperties().setAlternateDescription("Fox");
        p.add(foxImage);
        p.add(" jumps over the lazy ");
        Image dogImage = new Image(ImageDataFactory.create(dog));
        //Set alt text
        dogImage.getAccessibilityProperties().setAlternateDescription("Dog");
        p.add(dogImage);

        document.add(p);
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
     * 创建PDF，PDF/A-1b标准（快棕狐）
     */
    @GetMapping(value = "/quickBrownFoxPDFA_1b", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> quickBrownFoxPDFA_1b() throws IOException {
        final String dog = "src/main/resources/img/dog.bmp";
        final String fox = "src/main/resources/img/fox.bmp";
        final String myFont = "src/main/resources/font/FreeSans.ttf";
        final String intent = "src/main/resources/color/sRGB_CS_profile.icm";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDFA document with output intent
        PdfADocument pdf = new PdfADocument(new PdfWriter(outputStream),
                PdfAConformanceLevel.PDF_A_1B,
                new PdfOutputIntent("Custom", "", "http://www.color.org",
                        "sRGB IEC61966-2.1", new FileInputStream(intent)));
        Document document = new Document(pdf);

        //Fonts need to be embedded
        PdfFont font = PdfFontFactory.createFont(myFont, PdfEncodings.WINANSI, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        Paragraph p = new Paragraph();
        p.setFont(font);
        p.add(new Text("The quick brown "));
        Image foxImage = new Image(ImageDataFactory.create(fox));
        p.add(foxImage);
        p.add(" jumps over the lazy ");
        Image dogImage = new Image(ImageDataFactory.create(dog));
        p.add(dogImage);

        document.add(p);
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
     * 创建PDF，PDF/A-3a标准（快棕狐）
     */
    @GetMapping(value = "/quickBrownFoxPDFA_3a", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> quickBrownFoxPDFA_3a() throws IOException {
        final String data = "src/main/resources/data/united_states.csv";
        final String myFont = "src/main/resources/font/FreeSans.ttf";
        final String boldFont = "src/main/resources/font/FreeSansBold.ttf";
        final String intent = "src/main/resources/color/sRGB_CS_profile.icm";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfADocument pdf = new PdfADocument(new PdfWriter(outputStream),
                PdfAConformanceLevel.PDF_A_3A,
                new PdfOutputIntent("Custom", "", "http://www.color.org",
                        "sRGB IEC61966-2.1", new FileInputStream(intent)));
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        //Setting some required parameters
        pdf.setTagged();
        pdf.getCatalog().setLang(new PdfString("en-US"));
        pdf.getCatalog().setViewerPreferences(
                new PdfViewerPreferences().setDisplayDocTitle(true));
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("iText7 PDF/A-3 example");

        //Add attachment
        PdfDictionary parameters = new PdfDictionary();
        parameters.put(PdfName.ModDate, new PdfDate().getPdfObject());
        PdfFileSpec fileSpec = PdfFileSpec.createEmbeddedFileSpec(
                pdf, Files.readAllBytes(Paths.get(data)), "united_states.csv",
                "united_states.csv", new PdfName("text/csv"), parameters,
                PdfName.Data);
        fileSpec.put(new PdfName("AFRelationship"), new PdfName("Data"));
        pdf.addFileAttachment("united_states.csv", fileSpec);
        PdfArray array = new PdfArray();
        array.add(fileSpec.getPdfObject().getIndirectReference());
        pdf.getCatalog().put(new PdfName("AF"), array);

        //Embed fonts
        PdfFont font = PdfFontFactory.createFont(myFont, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        PdfFont bold = PdfFontFactory.createFont(boldFont, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

        // Create content
        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1}))
                .useAllAvailableWidth();

        BufferedReader br = new BufferedReader(new FileReader(data));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();
        document.add(table);

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

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().setHorizontalAlignment(HorizontalAlignment.CENTER).add(new Paragraph(tokenizer.nextToken()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(font)));
            } else {
                table.addCell(new Cell().setHorizontalAlignment(HorizontalAlignment.CENTER).add(new Paragraph(tokenizer.nextToken()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(font)));
            }
        }
    }

    /**
     * 根据PDF，合并PDF/A类文档（快棕狐+美国信息）
     */
    @GetMapping(value = "/mergePDFADocuments", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> mergePDFADocuments() throws IOException {
        final String intent = "src/main/resources/color/sRGB_CS_profile.icm";
        final String src1 = "src/main/resources/pdf/quick_brown_fox_PDFA-1a.pdf";
        final String src2 = "src/main/resources/pdf/united_states_PDFA-1a.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDFA document with output intent
        PdfADocument pdf = new PdfADocument(new PdfWriter(outputStream),
                PdfAConformanceLevel.PDF_A_1A,
                new PdfOutputIntent("Custom", "", "http://www.color.org",
                        "sRGB IEC61966-2.1", new FileInputStream(intent)));

        //Setting some required parameters
        pdf.setTagged();
        pdf.getCatalog().setLang(new PdfString("en-US"));
        pdf.getCatalog().setViewerPreferences(
                new PdfViewerPreferences().setDisplayDocTitle(true));
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("iText7 PDF/A-1a example");

        //Create PdfMerger instance
        PdfMerger merger = new PdfMerger(pdf);
        //Add pages from the first document
        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(src1));
        merger.merge(firstSourcePdf, 1, firstSourcePdf.getNumberOfPages());
        //Add pages from the second pdf document
        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(src2));
        merger.merge(secondSourcePdf, 1, secondSourcePdf.getNumberOfPages());

        //Close the documents
        firstSourcePdf.close();
        secondSourcePdf.close();
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
