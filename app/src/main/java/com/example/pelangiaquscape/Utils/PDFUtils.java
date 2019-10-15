package com.example.pelangiaquscape.Utils;

import android.graphics.pdf.PdfDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Penjualan;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class PDFUtils {

    Penjualan penjualan;
    AkunToko akunToko;
    Merek merek;

    public PDFUtils(){}

    public PDFUtils(Penjualan penjualan, AkunToko akunToko) {
        this.penjualan = penjualan;
        this.akunToko = akunToko;
    }

    public PDFUtils(Penjualan penjualan, AkunToko akunToko, Merek merek) {
        this.penjualan = penjualan;
        this.akunToko = akunToko;
        this.merek = merek;
    }

    public void createPdfForReceipt(){

        // create Calendar
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(penjualan.getTanggalPenjualan());

        SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");
        Date datea = c.getTime();
        String date  =formattedDate.format(datea);

        SimpleDateFormat fmt = new SimpleDateFormat("mm:ss");
        String hehe = fmt.format(datea);

        // direktori u/ menyimpan pdf
        String fpath = "/sdcard/testPdf"+hehe+".pdf";
        File file = new File(fpath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Font bfBold2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0,0,0));



        // create document
        Document document = new Document();

        // create table
        PdfPTable tbl = new PdfPTable(2); // buat table dgn 2 kolom
        PdfPCell cell = new PdfPCell();


        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            // nama toko
            cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
            String namaToko = akunToko.getNamaToko();
            cell.setPhrase(new Phrase(namaToko));
            tbl.addCell(cell);

            // alamat toko
            cell = new PdfPCell();
            cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
            String alamatToko = akunToko.getAlamat();
            cell.setPhrase(new Phrase(alamatToko));
            tbl.addCell(cell);

            // no telp toko
            cell = new PdfPCell();
            String noTelp = akunToko.getNoTelepon();
            cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
            cell.setPhrase(new Phrase(noTelp));
            tbl.addCell(cell);

            document.add(tbl);
            document.add(new Paragraph("=================================="));

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            // nama kasir
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Nama Kasir"));
            tbl.addCell(cell);

            cell.setPhrase(new Phrase(penjualan.getNamaPenjual()));
            tbl.addCell(cell);

            // tanggal penjualan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Tanggal"));
            tbl.addCell(cell);

            cell.setPhrase(new Phrase(date));
            tbl.addCell(cell);

            // waktu penjualan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Waktu"));
            tbl.addCell(cell);

            formattedDate = new SimpleDateFormat("HH:mm");
            date = formattedDate.format(datea);
            cell.setPhrase(new Phrase(date));
            tbl.addCell(cell);

            // nomor struk
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("No. Struk"));
            tbl.addCell(cell);

            cell.setPhrase(new Phrase(penjualan.getNoPenjualan()));
            tbl.addCell(cell);

            document.add(tbl);
            document.add(new Paragraph("=================================="));

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            double totalHargaSemuaItem = 0;
            for(ItemKeranjang keranjang: penjualan.getListItemKeranjang()){

                // kode penjualan
                cell = new PdfPCell();
                cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
                cell.setPhrase(new Phrase(keranjang.getKode()));
                tbl.addCell(cell);

                // harga * qty
                cell = new PdfPCell();
                String harga = String.valueOf(keranjang.getHargaJual());
                String qty = String.valueOf(keranjang.getQty());
                cell.setPhrase(new Phrase(harga + " x " + qty));
                tbl.addCell(cell);

                // total harga per item
                cell = new PdfPCell();
                String totalHargaPerItem = String.valueOf(keranjang.getTotalPrice());
                cell.setPhrase(new Phrase(totalHargaPerItem));
                tbl.addCell(cell);

                totalHargaSemuaItem += keranjang.getTotalPrice();


            }

            document.add(tbl);
            document.add(new Paragraph("=================================="));

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            // diskon
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Diskon"));
            tbl.addCell(cell);

            String diskon = String.valueOf(penjualan.getDiskon());
            cell.setPhrase(new Phrase(diskon));
            tbl.addCell(cell);

            // total harga all item
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Total Harga"));
            tbl.addCell(cell);

            String totalHarga = String.valueOf(totalHargaSemuaItem);
            cell.setPhrase(new Phrase(totalHarga));
            tbl.addCell(cell);

            document.add(tbl);
            document.add(new Paragraph("=================================="));

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            // bayar
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Bayar"));
            tbl.addCell(cell);

            String bayar = String.valueOf(penjualan.getUangBayar());
            cell.setPhrase(new Phrase(bayar));
            tbl.addCell(cell);

            // bayar
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Kembalian"));
            tbl.addCell(cell);

            String kembalian = String.valueOf(penjualan.getUangKembalian());
            cell.setPhrase(new Phrase(kembalian));
            tbl.addCell(cell);


            document.add(tbl);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
