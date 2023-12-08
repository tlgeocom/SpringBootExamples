package com.demo.controller;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * <p> @Title ITextChapter6Controller
 * <p> @Description iText Chapter-6测试Controller
 *
 * @author zhj
 * @date 2023/4/5 20:08
 */
@RestController
@RequestMapping("/chapter6")
public class ITextChapter6Controller {

    /**
     * 根据PDF，缩放尺寸（金门大桥）
     */
    @GetMapping(value = "/theGoldenGateBridgeScaleShrink", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdfWithLines() throws IOException {
        final String src = "src/main/resources/pdf/the_golden_gate_bridge.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        PdfDocument origPdf = new PdfDocument(new PdfReader(src));

        //Original page size
        PdfPage origPage = origPdf.getPage(1);
        Rectangle orig = origPage.getPageSizeWithRotation();

        //Add A4 page
        PdfPage page = pdf.addNewPage(PageSize.A4.rotate());
        //Shrink original page content using transformation matrix
        PdfCanvas canvas = new PdfCanvas(page);
        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(page.getPageSize().getWidth() / orig.getWidth(), page.getPageSize().getHeight() / orig.getHeight());
        canvas.concatMatrix(transformationMatrix);
        PdfFormXObject pageCopy = origPage.copyAsFormXObject(pdf);
        canvas.addXObjectAt(pageCopy, 0, 0);

        //Add page with original size
        pdf.addPage(origPage.copyTo(pdf));

        //Add A2 page
        page = pdf.addNewPage(PageSize.A2.rotate());
        //Scale original page content using transformation matrix
        canvas = new PdfCanvas(page);
        transformationMatrix = AffineTransform.getScaleInstance(page.getPageSize().getWidth() / orig.getWidth(), page.getPageSize().getHeight() / orig.getHeight());
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObjectAt(pageCopy, 0, 0);

        pdf.close();
        origPdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，一分为四（金门大桥）
     */
    @GetMapping(value = "/theGoldenGateBridgeTiles", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> theGoldenGateBridgeTiles() throws IOException {
        final String src = "src/main/resources/pdf/the_golden_gate_bridge.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        PdfDocument sourcePdf = new PdfDocument(new PdfReader(src));

        //Original page
        PdfPage origPage = sourcePdf.getPage(1);
        PdfFormXObject pageCopy = origPage.copyAsFormXObject(pdf);

        //Original page size
        Rectangle orig = origPage.getPageSize();
        //Tile size
        Rectangle tileSize = PageSize.A4.rotate();
        // Transformation matrix
        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(tileSize.getWidth() / orig.getWidth() * 2f, tileSize.getHeight() / orig.getHeight() * 2f);


        //The first tile
        PdfPage page = pdf.addNewPage(PageSize.A4.rotate());
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObjectAt(pageCopy, 0, -orig.getHeight() / 2f);

        //The second tile
        page = pdf.addNewPage(PageSize.A4.rotate());
        canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObjectAt(pageCopy, -orig.getWidth() / 2f, -orig.getHeight() / 2f);

        //The third tile
        page = pdf.addNewPage(PageSize.A4.rotate());
        canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObjectAt(pageCopy, 0, 0);

        //The fourth tile
        page = pdf.addNewPage(PageSize.A4.rotate());
        canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObjectAt(pageCopy, -orig.getWidth() / 2f, 0);

        pdf.close();
        sourcePdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，多页合并（金门大桥）
     */
    @GetMapping(value = "/theGoldenGateBridgeNUp", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> theGoldenGateBridgeNUp() throws IOException {
        final String src = "src/main/resources/pdf/the_golden_gate_bridge.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        PdfDocument sourcePdf = new PdfDocument(new PdfReader(src));

        //Original page
        PdfPage origPage = sourcePdf.getPage(1);

        //Original page size
        Rectangle orig = origPage.getPageSize();
        PdfFormXObject pageCopy = origPage.copyAsFormXObject(pdf);

        //N-up page
        PageSize nUpPageSize = PageSize.A4.rotate();
        PdfPage page = pdf.addNewPage(nUpPageSize);
        PdfCanvas canvas = new PdfCanvas(page);

        //Scale page
        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(nUpPageSize.getWidth() / orig.getWidth() / 2f, nUpPageSize.getHeight() / orig.getHeight() / 2f);
        canvas.concatMatrix(transformationMatrix);

        //Add pages to N-up page
        canvas.addXObjectAt(pageCopy, 0, orig.getHeight());
        canvas.addXObjectAt(pageCopy, orig.getWidth(), orig.getHeight());
        canvas.addXObjectAt(pageCopy, 0, 0);
        canvas.addXObjectAt(pageCopy, orig.getWidth(), 0);

        pdf.close();
        sourcePdf.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，进行拼接（第88届奥斯卡）
     */
    @GetMapping(value = "/the88thOscarCombine", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> the88thOscarCombine() throws IOException {
        final String src1 = "src/main/resources/pdf/88th_reminder_list.pdf";
        final String src2 = "src/main/resources/pdf/88th_noms_announcement.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document with output intent
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        PdfMerger merger = new PdfMerger(pdf);

        //Add pages from the first document
        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(src1));
        merger.merge(firstSourcePdf, 1, firstSourcePdf.getNumberOfPages());

        //Add pages from the second pdf document
        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(src2));
        merger.merge(secondSourcePdf, 1, secondSourcePdf.getNumberOfPages());

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

    /**
     * 根据PDF，抽取指定页数进行拼接（第88届奥斯卡）
     */
    @GetMapping(value = "/the88thOscarCombineXofY", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> the88thOscarCombineXofY() throws IOException {
        final String src1 = "src/main/resources/pdf/88th_reminder_list.pdf";
        final String src2 = "src/main/resources/pdf/88th_noms_announcement.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Initialize PDF document with output intent
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));

        PdfMerger merger = new PdfMerger(pdf);

        //Add pages from the first document
        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(src1));
        merger.merge(firstSourcePdf, Arrays.asList(1, 5, 7, 1));

        //Add pages from the second pdf document
        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(src2));
        merger.merge(secondSourcePdf, Arrays.asList(1, 15));

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

    /**
     * 存储目录清单
     */
    private static final Map<String, Integer> TheRevenantNominations = new TreeMap<String, Integer>();
    static {
        TheRevenantNominations.put("Performance by an actor in a leading role", 4);
        TheRevenantNominations.put("Performance by an actor in a supporting role", 4);
        TheRevenantNominations.put("Achievement in cinematography", 4);
        TheRevenantNominations.put("Achievement in costume design", 5);
        TheRevenantNominations.put("Achievement in directing", 5);
        TheRevenantNominations.put("Achievement in film editing", 6);
        TheRevenantNominations.put("Achievement in makeup and hairstyling", 7);
        TheRevenantNominations.put("Best motion picture of the year", 8);
        TheRevenantNominations.put("Achievement in production design", 8);
        TheRevenantNominations.put("Achievement in sound editing", 9);
        TheRevenantNominations.put("Achievement in sound mixing", 9);
        TheRevenantNominations.put("Achievement in visual effects", 10);
    }

    /**
     * 根据PDF，拼接并添加目录（第88届奥斯卡）
     */
    @GetMapping(value = "/the88thOscarCombineAddTOC", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> the88thOscarCombineAddTOC() throws IOException {
        final String src1 = "src/main/resources/pdf/88th_noms_announcement.pdf";
        final String src2 = "src/main/resources/pdf/oscars_movies_checklist_2016.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
        Document document = new Document(pdfDoc);
        document.add(new Paragraph(new Text("The Revenant nominations list"))
                .setTextAlignment(TextAlignment.CENTER));

        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(src1));
        for (Map.Entry<String, Integer> entry : TheRevenantNominations.entrySet()) {
            //Copy page
            PdfPage page  = firstSourcePdf.getPage(entry.getValue()).copyTo(pdfDoc);
            pdfDoc.addPage(page);

            //Overwrite page number
            Text text = new Text(String.format("Page %d", pdfDoc.getNumberOfPages() - 1));
            text.setBackgroundColor(ColorConstants.WHITE);
            document.add(new Paragraph(text).setFixedPosition(
                    pdfDoc.getNumberOfPages(), 549, 742, 100));

            //Add destination
            String destinationKey = "p" + (pdfDoc.getNumberOfPages() - 1);
            PdfArray destinationArray = new PdfArray();
            destinationArray.add(page.getPdfObject());
            destinationArray.add(PdfName.XYZ);
            destinationArray.add(new PdfNumber(0));
            destinationArray.add(new PdfNumber(page.getMediaBox().getHeight()));
            destinationArray.add(new PdfNumber(1));
            pdfDoc.addNamedDestination(destinationKey, destinationArray);

            //Add TOC line with bookmark
            Paragraph p = new Paragraph();
            p.addTabStops(new TabStop(540, TabAlignment.RIGHT, new DottedLine()));
            p.add(entry.getKey());
            p.add(new Tab());
            p.add(String.valueOf(pdfDoc.getNumberOfPages() - 1));
            p.setProperty(Property.ACTION, PdfAction.createGoTo(destinationKey));
            document.add(p);
        }
        firstSourcePdf.close();

        //Add the last page
        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(src2));
        PdfPage page  = secondSourcePdf.getPage(1).copyTo(pdfDoc);
        pdfDoc.addPage(page);

        //Add destination
        PdfArray destinationArray = new PdfArray();
        destinationArray.add(page.getPdfObject());
        destinationArray.add(PdfName.XYZ);
        destinationArray.add(new PdfNumber(0));
        destinationArray.add(new PdfNumber(page.getMediaBox().getHeight()));
        destinationArray.add(new PdfNumber(1));
        pdfDoc.addNamedDestination("checklist", destinationArray);

        //Add TOC line with bookmark
        Paragraph p = new Paragraph();
        p.addTabStops(new TabStop(540, TabAlignment.RIGHT, new DottedLine()));
        p.add("Oscars\u00ae 2016 Movie Checklist");
        p.add(new Tab());
        p.add(String.valueOf(pdfDoc.getNumberOfPages() - 1));
        p.setProperty(Property.ACTION, PdfAction.createGoTo("checklist"));
        document.add(p);
        secondSourcePdf.close();

        // close the document
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
     * 根据PDF，合并表格
     */
    @GetMapping(value = "/combineForms", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> combineForms() throws IOException {
        final String src1 = "src/main/resources/pdf/subscribe.pdf";
        final String src2 = "src/main/resources/pdf/state.pdf";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfDocument destPdfDocument = new PdfDocument(new PdfWriter(outputStream));
        PdfDocument[] sources = new PdfDocument[] {
                new PdfDocument(new PdfReader(src1)),
                new PdfDocument(new PdfReader(src2))
        };
        PdfPageFormCopier formCopier = new PdfPageFormCopier();
        for (PdfDocument sourcePdfDocument : sources) {
            sourcePdfDocument.copyPagesTo(
                    1, sourcePdfDocument.getNumberOfPages(),
                    destPdfDocument, formCopier);
            sourcePdfDocument.close();
        }
        destPdfDocument.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，批量填写并合并表格
     */
    @GetMapping(value = "/fillOutAndMergeForms", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> fillOutAndMergeForms() throws IOException {
        final String src = "src/main/resources/pdf/state.pdf";
        final String data = "src/main/resources/data/united_states.csv";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        PdfPageFormCopier formCopier = new PdfPageFormCopier();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(data));
        String line;
        boolean headerLine = true;
        int i = 1;
        while ((line = bufferedReader.readLine()) != null) {
            if (headerLine) {
                headerLine = false;
                continue;
            }

            com.itextpdf.io.source.ByteArrayOutputStream baos = new com.itextpdf.io.source.ByteArrayOutputStream();
            PdfDocument sourcePdfDocument = new PdfDocument(new PdfReader(src), new PdfWriter(baos));

            //Rename fields
            i++;
            PdfAcroForm form = PdfAcroForm.getAcroForm(sourcePdfDocument, true);
            form.renameField("name", "name_" + i);
            form.renameField("abbr", "abbr_" + i);
            form.renameField("capital", "capital_" + i);
            form.renameField("city", "city_" + i);
            form.renameField("population", "population_" + i);
            form.renameField("surface", "surface_" + i);
            form.renameField("timezone1", "timezone1_" + i);
            form.renameField("timezone2", "timezone2_" + i);
            form.renameField("dst", "dst_" + i);

            //Fill out fields
            StringTokenizer tokenizer = new StringTokenizer(line, ";");
            Map<String, PdfFormField> fields = form.getFormFields();
            fields.get("name_" + i).setValue(tokenizer.nextToken());
            fields.get("abbr_" + i).setValue(tokenizer.nextToken());
            fields.get("capital_" + i).setValue(tokenizer.nextToken());
            fields.get("city_" + i).setValue(tokenizer.nextToken());
            fields.get("population_" + i).setValue(tokenizer.nextToken());
            fields.get("surface_" + i).setValue(tokenizer.nextToken());
            fields.get("timezone1_" + i).setValue(tokenizer.nextToken());
            fields.get("timezone2_" + i).setValue(tokenizer.nextToken());
            fields.get("dst_" + i).setValue(tokenizer.nextToken());

            sourcePdfDocument.close();
            sourcePdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));

            //Copy pages
            sourcePdfDocument.copyPagesTo(1, sourcePdfDocument.getNumberOfPages(), pdfDocument, formCopier);
            sourcePdfDocument.close();
        }

        bufferedReader.close();
        pdfDocument.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    /**
     * 根据PDF，批量填写并合并表格（是否开启智能模式）
     */
    @GetMapping(value = "/fillOutFlattenAndMergeForms", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> fillOutFlattenAndMergeForms(boolean isSmartMode) throws IOException {
        final String src = "src/main/resources/pdf/state.pdf";
        final String data = "src/main/resources/data/united_states.csv";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfDocument destPdfDocument;
        if (isSmartMode) {
            // 在智能模式下，当遇到资源（如字体、图像等）时，对这些资源的引用将保存在缓存中，以便可以重复使用。这需要更多内存，但会减小生成的 PDF 文档的文件大小。
            // 参见：https://api.itextpdf.com/iText7/java/7.1.14/com/itextpdf/kernel/pdf/PdfWriter.html#setSmartMode-boolean-
            destPdfDocument = new PdfDocument(new PdfWriter(outputStream).setSmartMode(true));
        } else {
            destPdfDocument = new PdfDocument(new PdfWriter(outputStream));
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(data));
        String line;
        boolean headerLine = true;
        while ((line = bufferedReader.readLine()) != null) {
            if (headerLine) {
                headerLine = false;
                continue;
            }
            com.itextpdf.io.source.ByteArrayOutputStream baos = new com.itextpdf.io.source.ByteArrayOutputStream();
            PdfDocument sourcePdfDocument = new PdfDocument(new PdfReader(src), new PdfWriter(baos));

            //Read fields
            PdfAcroForm form = PdfAcroForm.getAcroForm(sourcePdfDocument, true);
            StringTokenizer tokenizer = new StringTokenizer(line, ";");
            Map<String, PdfFormField> fields = form.getFormFields();

            //Fill out fields
            fields.get("name").setValue(tokenizer.nextToken());
            fields.get("abbr").setValue(tokenizer.nextToken());
            fields.get("capital").setValue(tokenizer.nextToken());
            fields.get("city").setValue(tokenizer.nextToken());
            fields.get("population").setValue(tokenizer.nextToken());
            fields.get("surface").setValue(tokenizer.nextToken());
            fields.get("timezone1").setValue(tokenizer.nextToken());
            fields.get("timezone2").setValue(tokenizer.nextToken());
            fields.get("dst").setValue(tokenizer.nextToken());

            //Flatten fields
            form.flattenFields();

            sourcePdfDocument.close();
            sourcePdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));

            //Copy pages
            sourcePdfDocument.copyPagesTo(1, sourcePdfDocument.getNumberOfPages(), destPdfDocument, null);

            sourcePdfDocument.close();
        }

        bufferedReader.close();

        destPdfDocument.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=hello.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }
}
