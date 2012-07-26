/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffactory;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nimil
 */
public class CreatePDF {

    private String filePath;
    //Static Methods
    private static int noBorder = Rectangle.NO_BORDER;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);


    CreatePDF(String path, String csv) throws DocumentException, FileNotFoundException {
        this.filePath = path;
        init(filePath);
    }

    private void init(String path) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
        HeaderFooter event = new HeaderFooter();
        writer.setPageEvent(event);
        document.open();
        addMetaData(document);
        addContent(document);
        document.close();
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Itemisation Report");
        document.addSubject("Report for : ");
        document.addKeywords("Account Number : ");
        document.addAuthor("NC");
        document.addCreator("MakePositve Ltd.");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        
        
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("Invoice Number : 111111",smallFont));
        cell.setBorder(noBorder);
        table.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(noBorder);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Resilient Networks PLC 27 Shaftesbury Avenue London W1D 7EQ",smallFont));
        cell.setBorder(noBorder);
        table.addCell(cell);
        
        PdfPTable table1 = new PdfPTable(1);
        PdfPCell cell1;
        cell1 = new PdfPCell(new Phrase("Date (And Tax Point): 31-07-2011",smallFont));
        cell1.setBorder(noBorder);
        table1.addCell(cell1);
        // now we add a cell with rowspan 2
        cell1 = new PdfPCell(new Phrase("Billing Period (Ending) : 31-07-2011",smallFont));
        cell1.setBorder(noBorder);
        table1.addCell(cell1);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(50);
        table1.setWidthPercentage(100);
        table1.setHorizontalAlignment(50);
        
        document.add(table);
        document.add(table1);
        
        // Start a new page
        //document.newPage();
    }

    
    private static void addContent(Document document) throws DocumentException {
        Rectangle rect = new Rectangle(document.bottom(),document.left(),document.right(),document.right());
        Chunk chunk = new Chunk();
        chunk.setBackground(BaseColor.BLACK);
        Paragraph para = new Paragraph(chunk);
        para.add("Test");
        document.add(rect);
        document.add(para);
    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    /** Inner class to add a header and a footer. */
    static class HeaderFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage (PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            try {
                addTitlePage(document);
            } catch (DocumentException ex) {
                Logger.getLogger(CreatePDF.class.getName()).log(Level.SEVERE, null, ex);
            }
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", writer.getPageNumber())),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
        }
    }
}
