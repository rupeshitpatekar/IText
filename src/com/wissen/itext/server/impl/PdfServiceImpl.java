/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Rupeshit Patekar on 11:24:21 AM
PdfServiceImpl.java
 */
package com.wissen.itext.server.impl;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wissen.itext.client.services.PdfService;

/**
 * @author Rupeshit Patekar
 * 
 *         Create Date : 18-Jan-2010
 */
@SuppressWarnings("serial")
public class PdfServiceImpl extends RemoteServiceServlet implements PdfService {

    /***
     * This function returns full path of pdf
     * 
     * @param servlet
     * @return
     */
    String            username  = "root";

    String            password  = "wissen";

    Connection        con       = null;

    PreparedStatement stmt      = null;

    String            url       = "jdbc:mysql://localhost:3306/Employee";

    PrintWriter       out       = null;

    ResultSet         rsInvoice = null;

    String            invId, custId, custName, custAdd, desc, amt, subTot, taxRate, tax, other, tot, dateStr;

    String            RealUrl;

    public static String getUrl(Servlet servlet) {

        ServletContext servletContext = servlet.getServletConfig().getServletContext();
        String pdfUrl = servletContext.getRealPath("/");
        return pdfUrl;
    }

    public void retrieveData() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            if (con != null) {
                System.out.println("Connection Established");

                //TO fetch record from the database
                String query = "select * from Invoice where invoice_id = 62";
                System.out.println(query);
                Statement stmt = con.createStatement();
                rsInvoice = stmt.executeQuery(query);

                rsInvoice.next();
                //To store the value in variable
                invId = rsInvoice.getString(1);
                custId = rsInvoice.getString(2);
                custName = rsInvoice.getString(3);
                custAdd = rsInvoice.getString(4);
                desc = rsInvoice.getString(5);
                amt = rsInvoice.getString(6);
                subTot = rsInvoice.getString(7);
                taxRate = rsInvoice.getString(8);
                tax = rsInvoice.getString(9);
                other = rsInvoice.getString(10);
                tot = rsInvoice.getString(11);

                //To check value in ResultSet
                System.out.println(invId);
                System.out.println(custId);
                System.out.println(custName);

                //To get today date
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new java.util.Date();
                dateStr = dateFormat.format(date);

            } else {
                System.out.println("Connection does not Established");
            }
        } catch (Exception e) {
            System.out.println("Err" + e);
        }
    }

    @Override
    public String generatePdf() {
        try {
            // TODO Auto-generated method stub
            retrieveData();
            //To add back color
            Rectangle pageSize = new Rectangle(800, 800);
            // BaseColor backColor = new BaseColor(0xDF, 0xFF, 0xDE);
            //pageSize.setBackgroundColor(backColor);
            Document document = new Document(pageSize);

            //To add font color
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
            document.open();

            //To return path
            //RealUrl = getUrl(this);

            RealUrl = "http://localhost:8090/itext/Invoice.pdf";

            Font fontTableHeader = new Font(Font.COURIER, 15, Font.BOLD);
            BaseColor tableheaderColor = new BaseColor(0xf9, 0xf3, 0xf3);
            fontTableHeader.setColor(tableheaderColor);
            Font font = new Font(Font.TIMES_ROMAN, 20, Font.BOLD);
            BaseColor fontColor = new BaseColor(0x00, 0x00, 0x0);
            font.setColor(fontColor);
            Chunk chunkWissen = new Chunk("Wissen Labs", font);
            Paragraph paraWissen = new Paragraph();

            paraWissen.add(chunkWissen);
            paraWissen.setAlignment(Element.ALIGN_LEFT);

            Font invoiceFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD);
            BaseColor invoiceColor = new BaseColor(0x83, 0x94, 0xC9);
            invoiceFont.setColor(invoiceColor);
            Chunk invoices = new Chunk("INVOICE", invoiceFont);
            Paragraph invoicePara = new Paragraph();
            invoicePara.add(invoices);
            invoicePara.setAlignment(Element.ALIGN_RIGHT);

            //For invoice description
            Chunk invoiceDescript = new Chunk();
            Paragraph invoiceDescription = new Paragraph();
            invoiceDescription.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable tableDesc = new PdfPTable(2);
            PdfPCell dateString = new PdfPCell(new Paragraph("DATE:"));
            disableBorder(dateString);
            PdfPCell InvoiceString = new PdfPCell(new Paragraph("INVOICE#"));
            disableBorder(InvoiceString);
            PdfPCell CustString = new PdfPCell(new Paragraph("Customer ID"));
            disableBorder(CustString);
            PdfPCell dateCell = new PdfPCell(new Paragraph(dateStr));
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            BaseColor datecellColor = new BaseColor(0xE4, 0xE8, 0xF3);
            dateCell.setBackgroundColor(datecellColor);
            dateCell.disableBorderSide(PdfPCell.TOP);
            dateCell.disableBorderSide(PdfPCell.LEFT);
            dateCell.disableBorderSide(PdfPCell.RIGHT);

            PdfPCell Invoice = new PdfPCell(new Paragraph(invId));
            Invoice.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell CustId = new PdfPCell(new Paragraph(custId));
            CustId.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableDesc.addCell(dateString);
            tableDesc.addCell(dateCell);
            tableDesc.addCell(InvoiceString);
            tableDesc.addCell(Invoice);
            tableDesc.addCell(CustString);
            tableDesc.addCell(CustId);
            tableDesc.setTotalWidth(175);
            PdfContentByte cbx = writer.getDirectContent();
            tableDesc.writeSelectedRows(0, 3, 590, 700, cbx);

            //For address
            PdfPTable addrTable = new PdfPTable(1);
            Phrase address = new Phrase();
            address.add(Chunk.NEWLINE);
            address.add("4th Floor, Rajvi Enclave");
            address.add(Chunk.NEWLINE);
            address.add("New Pandit Colony, Nasik, Maharashtra, India");
            address.add(Chunk.NEWLINE);
            address.add("Phone: 91 0253 501 2345/91 0253 501 7876");
            Paragraph addressPara = new Paragraph();
            addressPara.add(address);
            addressPara.setAlignment(Element.ALIGN_LEFT);
            addrTable.addCell(addressPara);

            // For Bill paragraph
            Chunk billto = new Chunk("BILL TO:            ", fontTableHeader);
            BaseColor billtoColor = new BaseColor(0x3B, 0x4E, 0x87);
            billto.setBackground(billtoColor);
            Phrase bill_to_person = new Phrase();
            bill_to_person.add(Chunk.NEWLINE);
            bill_to_person.add(Chunk.NEWLINE);
            bill_to_person.add(Chunk.NEWLINE);
            bill_to_person.add(billto);
            bill_to_person.add(Chunk.NEWLINE);
            bill_to_person.add(custName);
            bill_to_person.add(Chunk.NEWLINE);
            bill_to_person.add("M2Wealth international limited");
            bill_to_person.add(Chunk.NEWLINE);
            bill_to_person.add(custAdd);
            Paragraph billToPara = new Paragraph();
            billToPara.add(bill_to_person);
            billToPara.setAlignment(Element.ALIGN_LEFT);

            // For Description and amount table
            //To create two cell
            float[] WidthCol = { 7f, 1f };

            PdfPCell descCell = new PdfPCell(new Paragraph("DESCRIPTION", fontTableHeader));
            descCell.setBackgroundColor(new BaseColor(0x3B, 0x4E, 0x87));
            descCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            descCell.disableBorderSide(PdfPCell.TOP);
            descCell.disableBorderSide(PdfPCell.BOTTOM);

            PdfPCell amtCell = new PdfPCell(new Paragraph("AMOUNT", fontTableHeader));
            amtCell.setBackgroundColor(new BaseColor(0x3B, 0x4E, 0x87));
            amtCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            amtCell.disableBorderSide(PdfPCell.TOP);
            amtCell.disableBorderSide(PdfPCell.BOTTOM);

            //To add cell into table
            PdfPTable tableInvoice = new PdfPTable(WidthCol);

            PdfPCell cell1 = new PdfPCell(new Paragraph(desc));
            cell1.disableBorderSide(PdfPCell.TOP);
            cell1.setExtraParagraphSpace(150f);

            PdfPCell cell2 = new PdfPCell(new Paragraph(amt));
            cell2.disableBorderSide(PdfPCell.TOP);
            cell2.setExtraParagraphSpace(150f);

            tableInvoice.addCell(descCell);
            tableInvoice.addCell(amtCell);
            tableInvoice.addCell(cell1);
            tableInvoice.addCell(cell2);
            tableInvoice.setWidthPercentage(100);

            //To add subtotal and other things

            PdfPCell subTotalCell = new PdfPCell(new Paragraph("SubTotal"));
            disableBorder(subTotalCell);
            subTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            subTotalCell.setBackgroundColor(new BaseColor(0xE4, 0xE8, 0xF3));
            subTotalCell.setExtraParagraphSpace(10f);
            //To add value in cell
            PdfPCell subTotalValueCell = new PdfPCell(new Paragraph("$" + subTot));
            disableBorder(subTotalValueCell);
            subTotalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            subTotalValueCell.setBackgroundColor(new BaseColor(0xE4, 0xE8, 0xF3));
            subTotalValueCell.setExtraParagraphSpace(10f);

            PdfPCell taxRateCell = new PdfPCell(new Paragraph("TaxRate"));
            disableBorder(taxRateCell);
            taxRateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            taxRateCell.setExtraParagraphSpace(10f);
            //To add value in cell
            PdfPCell taxRateValueCell = new PdfPCell(new Paragraph("$" + taxRate + ".00%"));
            disableBorder(taxRateValueCell);
            taxRateValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            taxRateValueCell.setExtraParagraphSpace(10f);

            PdfPCell taxCell = new PdfPCell(new Paragraph("Tax"));
            disableBorder(taxCell);
            taxCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            taxCell.setBackgroundColor(new BaseColor(0xE4, 0xE8, 0xF3));
            taxCell.setExtraParagraphSpace(10f);
            //To add value in cell
            PdfPCell taxValueCell = new PdfPCell(new Paragraph("$" + tax + ".00"));
            disableBorder(taxValueCell);
            taxValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            taxValueCell.setBackgroundColor(new BaseColor(0xE4, 0xE8, 0xF3));
            taxValueCell.setExtraParagraphSpace(10f);

            PdfPCell otherCell = new PdfPCell(new Paragraph("Other"));
            disableBorder(otherCell);
            otherCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            otherCell.setExtraParagraphSpace(10f);
            //To add value in cell
            PdfPCell otherValueCell = new PdfPCell(new Paragraph("$" + other + ".00"));
            disableBorder(otherValueCell);
            otherValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            otherValueCell.setExtraParagraphSpace(10f);

            PdfPCell totalCell = new PdfPCell(new Paragraph("Total"));
            totalCell.disableBorderSide(PdfPCell.BOTTOM);
            totalCell.disableBorderSide(PdfPCell.LEFT);
            totalCell.disableBorderSide(PdfPCell.RIGHT);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBackgroundColor(new BaseColor(0xE4, 0xE8, 0xF3));
            totalCell.setExtraParagraphSpace(10f);
            //To add value in cell
            PdfPCell totalValueCell = new PdfPCell(new Paragraph("$" + tot + ".00"));
            totalValueCell.disableBorderSide(PdfPCell.BOTTOM);
            totalValueCell.disableBorderSide(PdfPCell.LEFT);
            totalValueCell.disableBorderSide(PdfPCell.RIGHT);
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell.setBackgroundColor(new BaseColor(0xE4, 0xE8, 0xF3));
            totalValueCell.setExtraParagraphSpace(10f);

            PdfPTable otherTable = new PdfPTable(2);
            otherTable.addCell(subTotalCell);
            otherTable.addCell(subTotalValueCell);
            otherTable.addCell(taxRateCell);
            otherTable.addCell(taxRateValueCell);
            otherTable.addCell(taxCell);
            otherTable.addCell(taxValueCell);
            otherTable.addCell(otherCell);
            otherTable.addCell(otherValueCell);
            otherTable.addCell(totalCell);
            otherTable.addCell(totalValueCell);
            otherTable.setWidthPercentage(25);
            otherTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            //For table comment
            Font commentheaderFont = new Font(Font.COURIER, 10, Font.BOLD);
            PdfPCell comentHeaderCell = new PdfPCell(new Paragraph("OTHER COMMENTS", commentheaderFont));
            comentHeaderCell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
            Phrase commentsPhrase = new Phrase();
            commentsPhrase.add("1.Total payment due in 30 days");
            commentsPhrase.add(Chunk.NEWLINE);
            commentsPhrase.add("2.Please include the invoice number on your check");
            PdfPCell commentsCell = new PdfPCell(commentsPhrase);
            commentsCell.setExtraParagraphSpace(20f);
            PdfPTable commentsTable = new PdfPTable(1);
            commentsTable.setWidthPercentage(50);
            commentsTable.addCell(comentHeaderCell);
            commentsTable.addCell(commentsCell);
            commentsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            commentsTable.setTotalWidth(350f);
            commentsTable.writeSelectedRows(0, -1, 35, 300, writer.getDirectContent());

            //Check payable 
            Phrase check = new Phrase();
            check.add("Make checks payable to");
            check.add(Chunk.NEWLINE);
            check.add(Chunk.NEWLINE);
            Chunk checkchunk = new Chunk("Wissen Labs");
            check.add(checkchunk);
            Paragraph tipPara = new Paragraph();
            tipPara.add(check);
            tipPara.setAlignment(Element.ALIGN_RIGHT);

            // Adding footer
            Chunk footer = new Chunk("Thank You For Your Business!", font);
            Phrase footerPhrase = new Phrase();
            footerPhrase.add("If you have any questions about this invoice, please contact");
            footerPhrase.add(Chunk.NEWLINE);
            Font fontAnchor = new Font();
            fontAnchor.setColor(new BaseColor(0x00, 0x48, 0xFF));
            Chunk AnchorId = new Chunk("sushrut@wissen.co.in", fontAnchor);
            chunkWissen.setAnchor("http://google.com/");
            footerPhrase.add("Sushrut Bidwai,");
            footerPhrase.add(AnchorId);
            footerPhrase.add(", +91 986 023 8124");
            footerPhrase.add(Chunk.NEWLINE);
            footerPhrase.add(Chunk.NEWLINE);
            footerPhrase.add(footer);
            PdfContentByte cb1 = writer.getDirectContent();
            ColumnText ct1 = new ColumnText(cb1);
            ct1.setSimpleColumn(footerPhrase, 150, 20, 500, 115, 15, Element.ALIGN_CENTER);
            ct1.go();

            document.add(paraWissen);
            document.add(invoicePara);
            document.add(invoiceDescript);
            document.add(addressPara);
            document.add(billToPara);
            document.add(Chunk.NEWLINE);
            document.add(tableInvoice);
            document.add(otherTable);
            document.add(tipPara);

            document.close();
            System.out.println("PDF created successfully....");
            //  Window.alert("");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return RealUrl;
    }

    public void disableBorder(PdfPCell cell) {
        cell.disableBorderSide(PdfPCell.TOP);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.RIGHT);

    }
}
