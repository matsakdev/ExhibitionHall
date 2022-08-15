package com.matsak.exhibitionhall.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PDFGenerator {
//    public static final String FONT = "fonts/HelveticaRegular.ttf";
    public static final String FONT = "fonts/Yftoow-R.ttf";

    static {
        try{
            FontFactory.register(FONT, "HELVETICA_OWN");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    static Logger logger = LogManager.getLogger(PDFGenerator.class);
    public PDFGenerator() {}
    private static final int FONT_SIZE_SMALL = 16;
    private static final int FONT_SIZE_BIG = 32;
    private static final int OFFSET = 40;

    public static File ticketPDF(Ticket ticket) throws IOException, DocumentException {
        String docPath = System.getProperty("pdfPath") + "/" + ticket.getNum() + ".pdf";
        Exposition exposition = null;
        List<Ticket> currentTicket = new ArrayList<>();
        currentTicket.add(ticket);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            exposition = DAOFactory.getInstance().getExpositionDAO().getExpositionsByTickets(currentTicket).get(ticket);
        }
        catch (Exception ex) {
            logger.warn("Cannot get exposition for ticket " + ticket.getNum() + "; " + ex.getMessage());
        }

        Document document = new Document();
//        initializeDocument(document);
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 72, 36, 36);
        Font mainFont = null;
        Font smallFont = null;
        try{
            mainFont = FontFactory.getFont("HELVETICA_OWN", BaseFont.IDENTITY_H, true, FONT_SIZE_BIG, Font.BOLD);
            smallFont = FontFactory.getFont("HELVETICA_OWN", BaseFont.IDENTITY_H, true, FONT_SIZE_SMALL, Font.NORMAL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        FileOutputStream fos = new FileOutputStream(docPath);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("pdfPath") + "/" + ticket.getNum() + ".pdf"), StandardCharsets.UTF_8));
        PdfWriter writer = PdfWriter.getInstance(document, fos);



        document.open();

        //
        printText("Ticket " + ticket.getNum(), mainFont, Element.ALIGN_CENTER, true, document);

        //
        printText("Експозиція: " + exposition.getExpName(), smallFont, Element.ALIGN_CENTER, true, document);

        //
        printCellWithImage(getImage(System.getProperty("imagesPath") + "/kozaky.jpg", Element.ALIGN_RIGHT, 150, 250),
                getExpositionDates(exposition, dateFormat, smallFont, document), document);

        //
        printText("Інформація про власника квитка: \n" +
                ticket.getUser_login() + "\n" + ticket.getEmail() +
                "\nКвиток придбано: " + ticket.getOrder_date(), smallFont,
                Element.ALIGN_RIGHT, true, document);

//        printImage(getImage(System.getProperty("imagesPath") + "/kozaky.jpg", Element.ALIGN_RIGHT, 150, 250), document);


        document.close();
        return new File(docPath);
    }

    private static void printImage(Image image, Document document) {
        try{
            document.add(image);
        }
        catch (Exception ex) {
           logger.error(String.format("Cannot add image(%s) to document, " + ex.getMessage(), image.getUrl()));
        }
    }

    private static Image getImage(String imagePath, int align, float height, float width) {
        try{
            Image image = Image.getInstance(imagePath);
            image.setAlignment(align);
            image.scaleAbsoluteHeight(height);
            image.scaleAbsoluteWidth(width);
            return image;
        }
        catch (Exception ex) {
            logger.error(String.format("Cannot get image(%s), " + ex.getMessage(), imagePath));
            throw new IllegalArgumentException();
        }
    }

    private static Paragraph getExpositionDates(Exposition exposition, DateTimeFormatter dateFormat, Font font, Document document) {
        String startDate = exposition.getExpStartDate().toLocalDateTime().format(dateFormat);
        String endDate = exposition.getExpFinalDate().toLocalDateTime().format(dateFormat);

        Paragraph fullParagraph = new Paragraph();
        fullParagraph.add(getColorText("Дійсний з \n", font, Element.ALIGN_LEFT, document, BaseColor.BLACK));
        fullParagraph.add(getColorText(startDate, font, Element.ALIGN_LEFT, document, BaseColor.BLUE));
        fullParagraph.add(getColorText("\n до \n", font, Element.ALIGN_LEFT, document, BaseColor.BLACK));
        fullParagraph.add(getColorText(endDate, font, Element.ALIGN_LEFT, document, BaseColor.BLUE));

        return fullParagraph;
    }

    private static void printColorText(String text, Font font, int align, Document document, BaseColor color) {
        Font coloredFont = font.difference(font);
        coloredFont.setColor(color);
        Chunk coloredText = new Chunk(text);
        coloredText.setFont(coloredFont);
        printChunk(coloredText, align, document);
    }

    private static Paragraph getColorText(String text, Font font, int align, Document document, BaseColor color) {
        Font coloredFont = font.difference(font);
        coloredFont.setColor(color);
        Chunk coloredText = new Chunk(text);
        coloredText.setFont(coloredFont);
        Paragraph paragraph = new Paragraph(coloredText);
        paragraph.setAlignment(align);
        return paragraph;
    }

    private static void printChunk(Chunk coloredText, int align, Document document) {
        Paragraph paragraph = new Paragraph(coloredText);
        paragraph.setAlignment(align);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            logger.error(String.format("Cannot add chunk <%s> to document " + e.getMessage(), coloredText));
        }
    }

    private static void printText(String text, Font font, int align, boolean needSpacingAfter, Document document) {
        Paragraph title = new Paragraph(text, font);
        title.setAlignment(align);
        if (needSpacingAfter) title.setSpacingAfter(FONT_SIZE_BIG);
        try {
            document.add(title);
        } catch (DocumentException e) {
            logger.error(String.format("Cannot add text <%s> to document " + e.getMessage(), text));
        }
    }

    private static void printCellWithImage(Image image, Paragraph content, Document document) {
        PdfPTable table = new PdfPTable(2);
        PdfPCell textCell = new PdfPCell(content);
        textCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(textCell);

        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(imageCell);

        try {
            document.add(table);
        } catch (DocumentException e) {
            logger.error(String.format("Cannot add table to document " + e.getMessage()));
        }
    }

    private static void initializeDocument(Document document) {

    }
}
