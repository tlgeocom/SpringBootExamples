package com.demo.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * <p> @Title ITextChapter3Controller
 * <p> @Description iText Chapter-3测试Controller
 *
 * @author zhj
 * @date 2023/4/3 20:43
 */
@RestController
@RequestMapping("/chapter3")
public class ITextChapter3Controller {

    private static final String APPLE_IMG = "src/main/resources/img/ny_times_apple.jpg";
    private static final String APPLE_TXT = "src/main/resources/data/ny_times_apple.txt";
    private static final String FACEBOOK_IMG = "src/main/resources/img/ny_times_fb.jpg";
    private static final String FACEBOOK_TXT = "src/main/resources/data/ny_times_fb.txt";
    private static final String INST_IMG = "src/main/resources/img/ny_times_inst.jpg";
    private static final String INST_TXT = "src/main/resources/data/ny_times_inst.txt";

    static PdfFont timesNewRoman = null;
    static PdfFont timesNewRomanBold = null;

    /**
     * 创建PDF，简单的列渲染示例（纽约时代周刊）
     */
    @GetMapping(value = "/newYorkTimes", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> newYorkTimes() throws IOException {
        timesNewRoman = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        timesNewRomanBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        PageSize ps = PageSize.A5;

        // Initialize document
        Document document = new Document(pdf, ps);

        //Set column parameters
        float offSet = 36;
        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 3;
        float columnHeight = ps.getHeight() - offSet * 2;

        //Define column areas
        Rectangle[] columns = {new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight)};
        document.setRenderer(new ColumnDocumentRenderer(document, columns));

        Image apple = new Image(ImageDataFactory.create(APPLE_IMG)).setWidth(columnWidth);
        String articleApple = new String(Files.readAllBytes(Paths.get(APPLE_TXT)), StandardCharsets.UTF_8);
        addArticle(document, "Apple Encryption Engineers, if Ordered to Unlock iPhone, Might Resist", "By JOHN MARKOFF MARCH 18, 2016", apple, articleApple);
        Image facebook = new Image(ImageDataFactory.create(FACEBOOK_IMG)).setWidth(columnWidth);
        String articleFB = new String(Files.readAllBytes(Paths.get(FACEBOOK_TXT)), StandardCharsets.UTF_8);
        addArticle(document, "With \"Smog Jog\" Through Beijing, Zuckerberg Stirs Debate on Air Pollution", "By PAUL MOZUR MARCH 18, 2016", facebook, articleFB);
        Image inst = new Image(ImageDataFactory.create(INST_IMG)).setWidth(columnWidth);
        String articleInstagram = new String(Files.readAllBytes(Paths.get(INST_TXT)), StandardCharsets.UTF_8);
        addArticle(document, "Instagram May Change Your Feed, Personalizing It With an Algorithm","By MIKE ISAAC MARCH 15, 2016", inst, articleInstagram);

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
     * 添加文章
     * @param doc       文档
     * @param title     标题
     * @param author    作者
     * @param img       图片
     * @param text      文本
     * @throws IOException IO异常
     */
    private static void addArticle(Document doc, String title, String author, Image img, String text) throws IOException {
        Paragraph p1 = new Paragraph(title)
                .setFont(timesNewRomanBold)
                .setFontSize(14);
        doc.add(p1);
        doc.add(img);
        Paragraph p2 = new Paragraph()
                .setFont(timesNewRoman)
                .setFontSize(7)
                .setFontColor(ColorConstants.GRAY)
                .add(author);
        doc.add(p2);
        Paragraph p3 = new Paragraph()
                .setFont(timesNewRoman)
                .setFontSize(10)
                .add(text);
        doc.add(p3);
    }

    Color greenColor = new DeviceCmyk(0.78f, 0, 0.81f, 0.21f);
    Color yellowColor = new DeviceCmyk(0, 0, 0.76f, 0.01f);
    Color redColor = new DeviceCmyk(0, 0.76f, 0.86f, 0.01f);
    Color blueColor = new DeviceCmyk(0.28f, 0.11f, 0, 0);

    /**
     * 创建PDF，简单的表渲染示例（英格兰足球超级联赛）
     */
    @GetMapping(value = "/premierLeague", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> premierLeague() throws IOException {
        final String data = "src/main/resources/data/premier_league.csv";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        PageSize ps = new PageSize(842, 680);

        // Initialize document
        Document document = new Document(pdf, ps);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{1.5f, 7, 2, 2, 2, 2, 3, 4, 4, 2}));
        table
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(data), StandardCharsets.UTF_8));
        String line = br.readLine();
        processTable1(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            processTable1(table, line, font, false);
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

    /**
     * 处理表格
     * @param table     表格
     * @param line      行
     * @param font      字体
     * @param isHeader  是否是表头
     */
    private void processTable1(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        int columnNumber = 0;
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
                cell.setPadding(5).setBorder(null);
                table.addHeaderCell(cell);
            } else {
                columnNumber++;
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                cell.setFont(font).setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
                switch (columnNumber) {
                    case 4:
                        cell.setBackgroundColor(greenColor);
                        break;
                    case 5:
                        cell.setBackgroundColor(yellowColor);
                        break;
                    case 6:
                        cell.setBackgroundColor(redColor);
                        break;
                    default:
                        cell.setBackgroundColor(blueColor);
                        break;
                }
                table.addCell(cell);
            }
        }
    }


    /**
     * 自定义渲染器
     */
    private class RoundedCornersCellRenderer extends CellRenderer {
        public RoundedCornersCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void drawBorder(DrawContext drawContext) {
            Rectangle rectangle = getOccupiedAreaBBox();
            float llx = rectangle.getX() + 1;
            float lly = rectangle.getY() + 1;
            float urx = rectangle.getX() + getOccupiedAreaBBox().getWidth() - 1;
            float ury = rectangle.getY() + getOccupiedAreaBBox().getHeight() - 1;
            PdfCanvas canvas = drawContext.getCanvas();
            float r = 4;
            float b = 0.4477f;
            canvas.moveTo(llx, lly).lineTo(urx, lly).lineTo(urx, ury - r)
                    .curveTo(urx, ury - r * b, urx - r * b, ury, urx - r, ury)
                    .lineTo(llx + r, ury)
                    .curveTo(llx + r * b, ury, llx, ury - r * b, llx, ury - r)
                    .lineTo(llx, lly).stroke();
            super.drawBorder(drawContext);
        }
    }

    static PdfFont helvetica = null;
    static PdfFont helveticaBold = null;

    /**
     * 创建PDF，简单的事件渲染示例（UFO文件）
     */
    @GetMapping(value = "/ufo", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> ufo() throws IOException {
        final String data = "src/main/resources/data/ufo.csv";
        helvetica = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());

        // Initialize document
        Document document = new Document(pdf);
        Paragraph p = new Paragraph("List of reported UFO sightings in 20th century")
                .setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
        document.add(p);
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 5, 7, 4}));
        BufferedReader br = new BufferedReader(new FileReader(data));
        String line = br.readLine();
        processTable2(table, line, helveticaBold, true);
        while ((line = br.readLine()) != null) {
            processTable2(table, line, helvetica, false);
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
     * @param table     表格
     * @param line      行
     * @param font      字体
     * @param isHeader  是否是表头
     */
    private void processTable2(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            }
        }
    }


    /**
     * 自定义事件处理器
     */
    private class MyEventHandler implements IEventHandler {

        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            //Set background
            Color limeColor = new DeviceCmyk(0.208f, 0, 0.584f, 0);
            Color blueColor = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);
            pdfCanvas.saveState()
                    .setFillColor(pageNumber % 2 == 1 ? limeColor : blueColor)
                    .rectangle(pageSize.getLeft(), pageSize.getBottom(), pageSize.getWidth(), pageSize.getHeight())
                    .fill().restoreState();

            //Add header and footer
            pdfCanvas.beginText()
                    .setFontAndSize(helvetica, 9)
                    .moveText(pageSize.getWidth() / 2 - 60, pageSize.getTop() - 20)
                    .showText("THE TRUTH IS OUT THERE")
                    .moveText(60, -pageSize.getTop() + 30)
                    .showText(String.valueOf(pageNumber))
                    .endText();

            //Add watermark
            Canvas canvas = new Canvas(pdfCanvas, page.getPageSize());
            canvas.setFontColor(ColorConstants.WHITE);
            canvas.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(60));
            canvas.setProperty(Property.FONT, helveticaBold);
            canvas.showTextAligned(new Paragraph("CONFIDENTIAL"), 298, 421, pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

            pdfCanvas.release();
        }
    }

}
