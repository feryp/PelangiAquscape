package com.example.pelangiaquscape.Utils;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.Model.Pembelian;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FakturUtils {
    Pembelian pembelian;
    AkunToko akunToko;
    Merek merek;
    String key;
    Pemasok pemasok;
    int no;


    public FakturUtils(){}

    public FakturUtils(Pembelian pembelian, AkunToko akunToko) {
        this.pembelian = pembelian;
        this.akunToko = akunToko;
    }

    public  void createPdfForFaktur(){
        //create Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pembelian.getTanggalPesanan());

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
        Date date = calendar.getTime();
        String tanggal = formatTgl.format(date);

//        direktori untuk menyimpan pdf
        String path = "/sdcard/" + pembelian.getNoPesanan() + ".pdf";
        File file = new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        //create faktur document
        Document document = new Document(PageSize.LEGAL);
        document.setMargins(5,5,5,5);

        //create table
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            document.add(table);
            Paragraph p = new Paragraph("Faktur Pembelian");
            p.setLeading(0,1);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);


            //nama toko
            cell.setColspan(2); //collspan buat mak 5 kolom (merge cell 2 column)
            String namaToko = akunToko.getNamaToko();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fonNamaToko = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0,0,0));
            Paragraph paragraph = new Paragraph(namaToko);
            paragraph.setLeading(0,1);
            cell.addElement(paragraph);
            cell.setBorder(0);
            cell.setPhrase(new Phrase(namaToko, fonNamaToko));
            table.addCell(cell);

            //alamat toko
            cell = new PdfPCell();
            cell.setColspan(2);
            String alamatToko = akunToko.getAlamat();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fontAlamatToko = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph paragraph1 = new Paragraph(alamatToko);
            paragraph1.setLeading(0,1);
            cell.addElement(paragraph1);
            cell.setBorder(0);
            table.addCell(cell);

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
            table.addCell(cell);

            table = new PdfPTable(4);

            //no faktur
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("No. Faktur :"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String noFaktur = "00" + no;
            Font fontNoFaktur = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pNoFaktur = new Paragraph(noFaktur);
            pNoFaktur.setLeading(0,1);
            cell.addElement(pNoFaktur);
            cell.setPhrase(new Phrase(noFaktur, fontNoFaktur));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);

            //No. Pesanan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("No. Pesanan :"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String noPesanan = this.pembelian.getNoPesanan();
            Font fontNoPesanan = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pNoPesanan = new Paragraph(noPesanan);
            pNoPesanan.setLeading(0,1);
            cell.addElement(pNoPesanan);
            cell.setPhrase(new Phrase(noPesanan, fontNoPesanan));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);

            //tgl faktur
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Tanggal Faktur :"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String tglFaktur = tanggal;
            Font fontTglFakktur = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pTglFaktur = new Paragraph(tglFaktur);
            pTglFaktur.setLeading(0,1);
            cell.addElement(pTglFaktur);
            cell.setPhrase(new Phrase(tglFaktur, fontTglFakktur));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);

            //Nama Pemasok
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Pemasok :"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String namaPemasok = this.pemasok.getNamaPemasok();
            Font fontNamaPemasok = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pNamaPemasok = new Paragraph(namaPemasok);
            pNamaPemasok.setLeading(0,1);
            cell.addElement(pNamaPemasok);
            cell.setPhrase(new Phrase(namaPemasok, fontNamaPemasok));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);

            table = new PdfPTable(2); //buat table dgn 5 kolom

            double totalHargaItem = 0;
            for (ItemKeranjang keranjang : this.pembelian.getListBarang()){

                //kode barang
                cell = new PdfPCell();
                cell.setColspan(2);//collspan buat make 2 kolom (merge cell 2 kolom)
                cell.setBorder(1);
                cell.setPhrase(new Phrase(keranjang.getKode()));
                table.addCell(cell);

                //harga * qty
                cell = new PdfPCell();
                String hargaBarang = decimalFormat.format(keranjang.getHargaBeli());
                String qty = String.valueOf(keranjang.getQty());
                cell.setPhrase(new Phrase("Rp. " + hargaBarang + " x " + qty));
                cell.setBorder(1);
                table.addCell(cell);

                //total harga per item
                cell = new PdfPCell();
                String totalHargaPerItem = decimalFormat.format(keranjang.getTotalPrice());
                Font fontTotalItem = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
                Paragraph pTotalItem = new Paragraph(totalHargaPerItem);
                pTotalItem.setLeading(0,1);
                cell.addElement(pTotalItem);
                cell.setPhrase(new Phrase("Rp. " + totalHargaPerItem, fontTotalItem));
                cell.setBorder(1);
                table.addCell(cell);

                totalHargaItem += keranjang.getTotalPrice();

            }

            //total harga all item
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Total Harga : "));
            cell.setBorder(0);
            table.addCell(cell);

            String totalHarga = decimalFormat.format(totalHargaItem);
            Font fontTotalHarga = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0,0,0));
            Paragraph pTotalHarga = new Paragraph(totalHarga);
            pTotalHarga.setLeading(0,1);
            cell.setPhrase(new Phrase("Rp. " + totalHarga, fontTotalHarga));
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);


            document.add(table);
            document.close();
        } catch (DocumentException e){
            e.printStackTrace();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        }


    }

}
