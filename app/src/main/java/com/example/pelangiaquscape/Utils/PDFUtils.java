package com.example.pelangiaquscape.Utils;

import android.graphics.pdf.PdfDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class PDFUtils {

    Penjualan penjualan;
    AkunToko akunToko;
    Merek merek;
    String key;
    String namaKasir;


    public PDFUtils(AkunToko akunToko){}

    public PDFUtils(Penjualan penjualan, AkunToko akunToko) {
        this.penjualan = penjualan;
        this.akunToko = akunToko;
    }

    public PDFUtils(Penjualan penjualan, AkunToko akunToko, String namaKasir) {
        this.penjualan = penjualan;
        this.akunToko = akunToko;
        this.namaKasir = namaKasir;
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

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

        SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");
        Date datea = c.getTime();
        String date  = formattedDate.format(datea);

        SimpleDateFormat fmt = new SimpleDateFormat("mm:ss");
        String hehe = fmt.format(datea);

        // direktori u/ menyimpan pdf
        String fpath = "/sdcard/"+penjualan.getNoPenjualan()+".pdf";
        File file = new File(fpath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0,0,0));


        // create document
        Document document = new Document(PageSize.SMALL_PAPERBACK);
        document.setMargins(10,10,20,20);

        // create table
        PdfPTable tbl = new PdfPTable(2); // buat table dgn 2 kolom
        PdfPCell cell = new PdfPCell();


        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            // nama toko

            cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
            String namaToko = akunToko.getNamaToko();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fontNamaToko = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0,0,0));
            Paragraph paragraph = new Paragraph(namaToko);
            paragraph.setLeading(0, 1);
            cell.addElement(paragraph);
            cell.setBorder(0);
            cell.setPhrase(new Phrase(namaToko, fontNamaToko));
            tbl.addCell(cell);

            // alamat toko
            cell = new PdfPCell();
            cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
            String alamatToko = akunToko.getAlamat();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fontAlamatToko = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph paragraph1 = new Paragraph(alamatToko);
            paragraph.setLeading(0, 1);
            cell.addElement(paragraph1);
            cell.setBorder(0);
            cell.setPhrase(new Phrase(alamatToko, fontAlamatToko));
            tbl.addCell(cell);

            // no telp toko
            cell = new PdfPCell();
            String noTelp = akunToko.getNoTelepon();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fontNoTelp = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph paragraph2 = new Paragraph(noTelp);
            paragraph.setLeading(0, 1);
            cell.addElement(paragraph2);
            cell.setBorder(0);
            cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
            cell.setPhrase(new Phrase(noTelp, fontNoTelp));
            tbl.addCell(cell);

            document.add(tbl);
            Paragraph p = new Paragraph("------------------------------------------------------------------------");
            p.setLeading(0,1);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);



            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            // nama kasir
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Nama Kasir"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tbl.addCell(cell);

            String nama;
            if(this.namaKasir != null){
                nama = this.namaKasir;
            }else{
                nama = this.penjualan.getNamaPenjual();
            }

            Font fontNamaKasir = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pNamaKasir = new Paragraph(nama);
            paragraph.setLeading(0, 1);
            cell.addElement(pNamaKasir);
            cell.setPhrase(new Phrase(nama, fontNamaKasir));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

            // tanggal penjualan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Tanggal"));
            cell.setBorder(0);
            tbl.addCell(cell);

            String tgl = date;
            Font fontTgl = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pTgl = new Paragraph(tgl);
            pTgl.setLeading(0, 1);
            cell.addElement(pTgl);
            cell.setPhrase(new Phrase(tgl, fontTgl));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

            // waktu penjualan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Waktu"));
            cell.setBorder(0);
            tbl.addCell(cell);


            formattedDate = new SimpleDateFormat("HH:mm");
            String waktu = formattedDate.format(datea);
            Font fontWaktu = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pWaktu = new Paragraph(waktu);
            pWaktu.setLeading(0, 1);
            cell.addElement(pWaktu);
            cell.setPhrase(new Phrase(waktu, fontWaktu));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

            // nomor struk
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("No. Struk"));
            cell.setBorder(0);
            tbl.addCell(cell);

            String noPenjualan = this.penjualan.getNoPenjualan();
            Font fontNoPenjualan = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pNoPenjualan = new Paragraph(tgl);
            pNoPenjualan.setLeading(0, 1);
            cell.addElement(pNoPenjualan);
            cell.setPhrase(new Phrase(noPenjualan, fontNoPenjualan));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

            document.add(tbl);
            Paragraph p1 = new Paragraph("------------------------------------------------------------------------");
            p1.setLeading(0,1);
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            double totalHargaSemuaItem = 0;
            for(ItemKeranjang keranjang: this.penjualan.getListItemKeranjang()){

                // kode penjualan
                cell = new PdfPCell();
                cell.setColspan(2); // collspan buat make 2 kolom ( merge cell 2 column)
                cell.setPhrase(new Phrase(keranjang.getKode()));
                cell.setBorder(0);
                tbl.addCell(cell);

                // harga * qty
                cell = new PdfPCell();
                String harga = decimalFormat.format(keranjang.getHargaJual());
                String qty = String.valueOf(keranjang.getQty());
                cell.setPhrase(new Phrase("Rp. " + harga + " x " + qty));
                cell.setBorder(0);
                tbl.addCell(cell);

                // total harga per item
                cell = new PdfPCell();
                String totalHargaPerItem = decimalFormat.format(keranjang.getTotalPrice());
                Font fontTotalItem = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
                Paragraph pTotalItem = new Paragraph(totalHargaPerItem);
                pTotalItem.setLeading(0, 1);
                cell.addElement(pTotalItem);
                cell.setPhrase(new Phrase("Rp. " + totalHargaPerItem, fontTotalItem));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tbl.addCell(cell);

                totalHargaSemuaItem += keranjang.getTotalPrice();


            }

            document.add(tbl);
            Paragraph p2 = new Paragraph("------------------------------------------------------------------------");
            p2.setLeading(0,1);
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            // diskon
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Diskon"));
            cell.setBorder(0);
            tbl.addCell(cell);


            String diskon = String.valueOf(this.penjualan.getDiskon());
            Font fontDiskon = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pDiskon = new Paragraph(diskon);
            pDiskon.setLeading(0, 1);
            cell.addElement(pDiskon);
            cell.setPhrase(new Phrase(diskon, fontDiskon));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

//            document.add(tbl);
//            Paragraph p3 = new Paragraph("----------------------------------------------------------------------------------------------------------------------");
//            p3.setLeading(0,1);
//            p3.setAlignment(Element.ALIGN_CENTER);
//            document.add(p3);


            // total harga all item
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Total Harga"));
            cell.setBorder(0);
            tbl.addCell(cell);


            String totalHarga = decimalFormat.format(totalHargaSemuaItem);
            Font fontTotalHarga = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pTotalHarga = new Paragraph(totalHarga);
            pTotalHarga.setLeading(0, 1);
            cell.addElement(pTotalHarga);
            cell.setPhrase(new Phrase("Rp. " + totalHarga, fontTotalHarga));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

            document.add(tbl);
            Paragraph p4 = new Paragraph("------------------------------------------------------------------------");
            p4.setLeading(0,1);
            p4.setAlignment(Element.ALIGN_CENTER);
            document.add(p4);

            tbl = new PdfPTable(2); // buat table dgn 2 kolom

            // bayar
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Bayar"));
            cell.setBorder(0);
            tbl.addCell(cell);

            String bayar = decimalFormat.format(this.penjualan.getUangBayar());
            Font fontBayar = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pBayar = new Paragraph(bayar);
            pBayar.setLeading(0, 1);
            cell.addElement(pBayar);
            cell.setPhrase(new Phrase("Rp. " + bayar, fontBayar));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tbl.addCell(cell);

            // bayar
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Kembalian"));
            cell.setBorder(0);
            tbl.addCell(cell);

            String kembalian = decimalFormat.format(this.penjualan.getUangKembalian());
            Font fontKembalian = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pKembalian = new Paragraph(kembalian);
            pKembalian.setLeading(0, 1);
            cell.addElement(pKembalian);
            cell.setPhrase(new Phrase("Rp. " + kembalian, fontKembalian));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
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
