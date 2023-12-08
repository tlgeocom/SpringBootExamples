package com.demo.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.StringTokenizer;

/**
 * <p> @Title ITextTestController
 * <p> @Description iText Chapter-1测试Controller
 *
 * @author zhj
 * @date 2023/4/3 16:28
 */
@RestController
@RequestMapping("/chapter1")
public class ITextChapter1Controller {

    /**
     * 创建PDF，并写入Hello World
     */
    @GetMapping(value = "/helloWorld", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> helloWorld() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // 写入 Hello World
        document.add(new Paragraph("Hello World!"));
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
     * 创建PDF，定制字体并写入多行（Rick Astley 歌词）
     */
    @GetMapping(value = "/createPdfWithLines", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithLines() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // 定制字体，并写入多行
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        document.add(new Paragraph("iText is:").setFont(font));
        // import com.itextpdf.layout.element.List
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022")
                .setFont(font);
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))
                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        document.add(list);
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
     * 创建PDF，定制字体并写入文字和图片（快棕狐）
     */
    @GetMapping(value = "/createPdfWithImg", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithImg() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // 插入图片
        final String foxImgPath = "src/main/resources/img/fox.bmp";
        final String dogImgPath = "src/main/resources/img/dog.bmp";
        Image fox = new Image(ImageDataFactory.create(foxImgPath));
        Image dog = new Image(ImageDataFactory.create(dogImgPath));
        Paragraph p = new Paragraph("The quick brown ")
                .add(fox)
                .add(" jumps over the lazy ")
                .add(dog);
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
     * 创建PDF，定制字体并根据csv文件写入表格（美国城市信息）
     */
    @GetMapping(value = "/createPdfWithTable", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithTable() throws IOException {
        final String data = "src/main/resources/data/united_states.csv";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        // 根据csv文件写入表格
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
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
     * 处理表格
     * @param table 表格
     * @param line  csv中一行内容
     * @param font  字体
     * @param isHeader  是否是表头
     */
    private void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }


}
