package com.example.pelangiaquscape.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.Model.Pembelian;
import com.ibm.icu.text.RuleBasedNumberFormat;
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
import com.itextpdf.text.pdf.parser.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FakturUtils {
    Pembelian pembelian;
    AkunToko akunToko;
    Merek merek;
    String key;
    Pemasok pemasok;
    int no;

    SharedPreferences preferences;
    private Context context;


    public FakturUtils(Pembelian pembelian, AkunToko akunToko) {
        this.pembelian = pembelian;
        this.akunToko = akunToko;
    }

    public FakturUtils(Pembelian pembelian, AkunToko akunToko, Context context) {
        this.context = context;
        this.pembelian = pembelian;
        this.akunToko = akunToko;
    }

    public void createPdfForFaktur() {
        //create Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pembelian.getTanggalPesanan());


        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
        Date date = calendar.getTime();
        String tanggal = formatTgl.format(date);
        preferences = context.getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);

//        direktori untuk menyimpan pdf
        String path = "/sdcard/" + pembelian.getNoPesanan().replaceAll("[^a-zA-Z0-9]", "") + ".pdf";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //create faktur document
        Document document = new Document();
        document.setPageSize(PageSize.SMALL_PAPERBACK.rotate());
        document.setMargins(5, 5, 5, 5);


        //create table
        PdfPTable table = new PdfPTable(5);
        PdfPCell cell = new PdfPCell();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            document.add(table);
            Paragraph p = new Paragraph("Faktur Pembelian");
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);


            //nama toko
            cell.setColspan(5); //collspan buat mak 5 kolom (merge cell 2 column)
            String namaToko = akunToko.getNamaToko();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fonNamaToko = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0));
            Paragraph paragraph = new Paragraph(namaToko);
            paragraph.setLeading(0, 1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            cell.setBorder(0);
            cell.setPhrase(new Phrase(namaToko, fonNamaToko));
            table.addCell(cell);


            //alamat toko
            cell = new PdfPCell();
            cell.setColspan(5);
            String alamatToko = akunToko.getAlamat();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fontAlamatToko = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
            Paragraph paragraph1 = new Paragraph(alamatToko);
            paragraph1.setLeading(0, 1);
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph1);
            cell.setBorder(0);
            cell.setPhrase(new Phrase(alamatToko, fontAlamatToko));
            table.addCell(cell);

            // no telp toko
            cell = new PdfPCell();
            cell.setColspan(5); // collspan buat make 2 kolom ( merge cell 2 column)
            String noTelp = akunToko.getNoTelepon();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font fontNoTelp = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
            Paragraph paragraph2 = new Paragraph(noTelp);
            paragraph2.setLeading(0, 1);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph2);
            cell.setBorder(0);
            cell.setPhrase(new Phrase(noTelp, fontNoTelp));
            table.addCell(cell);


            document.add(table);
            Paragraph p1 = new Paragraph();
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);

            table = new PdfPTable(4);
            table.setSpacingBefore(5);

            //no faktur
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("No. Faktur         :"));
            cell.setVerticalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);


            String noFaktur = "00" + no;
            Font fontNoFaktur = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(0, 0, 0));
            Paragraph pNoFaktur = new Paragraph(noFaktur);
            cell.addElement(pNoFaktur);
            cell.setPhrase(new Phrase(noFaktur, fontNoFaktur));
            cell.setBorder(0);
            table.addCell(cell);

            //No. Pesanan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("No. Pesanan :"));
            cell.setVerticalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String noPesanan = this.pembelian.getNoPesanan();
            Font fontNoPesanan = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(0, 0, 0));
            Paragraph pNoPesanan = new Paragraph(noPesanan);
            pNoPesanan.setLeading(0, 1);
            cell.addElement(pNoPesanan);
            cell.setPhrase(new Phrase(noPesanan, fontNoPesanan));
            cell.setBorder(0);
            table.addCell(cell);

            document.add(table);
            Paragraph garis1 = new Paragraph();
            garis1.setLeading(0,1);
            garis1.setAlignment(Element.ALIGN_CENTER);
            document.add(garis1);
//
            table = new PdfPTable(4);

            //tgl faktur
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Tanggal Faktur :"));
            cell.setVerticalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String tglFaktur = tanggal;
            Font fontTglFakktur = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(0, 0, 0));
            Paragraph pTglFaktur = new Paragraph(tglFaktur);
            pTglFaktur.setLeading(0, 1);
            cell.addElement(pTglFaktur);
            cell.setPhrase(new Phrase(tglFaktur, fontTglFakktur));
            cell.setBorder(0);
            table.addCell(cell);


            //Nama Pemasok
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Pemasok       :"));
            cell.setVerticalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            String namaPemasok = pembelian.getNamaPemasok();
            Font fontNamaPemasok = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(0, 0, 0));
            Paragraph pNamaPemasok = new Paragraph(namaPemasok);
            pNamaPemasok.setLeading(0, 1);
            cell.addElement(pNamaPemasok);
            if (namaPemasok != null) {
                cell.setPhrase(new Phrase(namaPemasok, fontNamaPemasok));
            } else {
                cell.setPhrase(new Phrase("unknown", fontNamaPemasok));
            }

            cell.setBorder(0);
            table.addCell(cell);

            document.add(table);
            Paragraph p2 = new Paragraph();
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            table = new PdfPTable(5);//buat table dgn 5 kolom
            table.setSpacingBefore(15);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Kode Barang"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Merek"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Kts"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Harga Satuan"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Jumlah"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

//            document.add(table);
//            Paragraph p3 = new Paragraph();
//            p3.setAlignment(Element.ALIGN_CENTER);
//            document.add(p3);


            double totalHargaItem = 0;
            for (ItemKeranjang keranjang : this.pembelian.getListBarang()) {



                document.add(table);
                Paragraph p4 = new Paragraph();
                p4.setAlignment(Element.ALIGN_CENTER);
                document.add(p4);

                table = new PdfPTable(5);

                //kode barang
                cell = new PdfPCell();//collspan buat make 2 kolom (merge cell 2 kolom)
                String kodeBarang = String.valueOf(keranjang.getKode());
                Font fontKodeBarang = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
                cell.setPhrase(new Phrase(kodeBarang, fontKodeBarang));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                //merek barang
                cell = new PdfPCell();
                int noMerek = Integer.valueOf(keranjang.getMerek());
                String merekBarang = preferences.getString(String.valueOf(noMerek), "unknown");
                Font fontMerekBarang = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
                cell.setPhrase(new Phrase(merekBarang, fontMerekBarang));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);


                // qty
                cell = new PdfPCell();
                String qty = String.valueOf(keranjang.getQty());
                Font fontQty = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
                cell.setPhrase(new Phrase(qty, fontQty));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);


                //total harga per item
                cell = new PdfPCell();
                String totalHargaPerItem = decimalFormat.format(keranjang.getHargaBeli());
                Font fontTotalItem = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
                cell.setPhrase(new Phrase("Rp. " + totalHargaPerItem, fontTotalItem));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                //jumlah
                cell = new PdfPCell();
                String jumlah = decimalFormat.format(keranjang.getTotalPrice());
                Font fontJumlah = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
                cell.setPhrase(new Phrase("Rp. " + jumlah, fontJumlah));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);



            }

            double total = 0;
            for (ItemKeranjang keranjang : this.pembelian.getListBarang()){
                total =  total + keranjang.getTotalPrice();

            }

            document.add(table);
            Paragraph p4 = new Paragraph();
            p4.setAlignment(Element.ALIGN_CENTER);
            document.add(p4);

            table = new PdfPTable(4);
            table.setSpacingBefore(2);

            //terbilang
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Terbilang    :"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            table.addCell(cell);

            Locale local = new Locale("id_ID");
            RuleBasedNumberFormat rbf = new RuleBasedNumberFormat(local,
                    RuleBasedNumberFormat.SPELLOUT);
            String terbilang = rbf.format(total);
            Font fontTerbilang = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC, new BaseColor(0, 0, 0));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPhrase(new Phrase(terbilang, fontTerbilang));
            cell.setBorder(0);
            table.addCell(cell);

            //Total Harga
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Total    :"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            table.addCell(cell);

            String totalHarga = decimalFormat.format(total);
            Font fontTotalHarga = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Paragraph pTotalHarga = new Paragraph(totalHarga);
            pTotalHarga.setLeading(0, 1);
            cell.setPhrase(new Phrase("Rp. " + totalHarga, fontTotalHarga));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            document.add(table);
            Paragraph p5 = new Paragraph();
            p5.setAlignment(Element.ALIGN_CENTER);
            document.add(p5);

            table = new PdfPTable(1);

            //keterangan
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Keterangan    :"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            document.add(table);
            Paragraph p6 = new Paragraph();
            p6.setAlignment(Element.ALIGN_CENTER);
            document.add(p6);

            table = new PdfPTable(2);
            table.setSpacingBefore(2);

            //pengirim dan penerima
            cell = new PdfPCell();
            Font fontPengirim = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            cell.setPhrase(new Phrase("PENGIRIM", fontPengirim));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell();
            Font fontPenerima = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            cell.setPhrase(new Phrase("PENERIMA", fontPenerima));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);


            document.add(table);
            Paragraph p7 = new Paragraph();
            p7.setAlignment(Element.ALIGN_CENTER);
            document.add(p7);

            table = new PdfPTable(2);
            table.setSpacingBefore(20);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("----------------"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("----------------"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);





//            String noPesanan = this.pembelian.getNoPesanan();
//            Font fontNoPesanan = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(0, 0, 0));
//            Paragraph pNoPesanan = new Paragraph(noPesanan);
//            pNoPesanan.setLeading(0, 1);
//            cell.addElement(pNoPesanan);
//            cell.setPhrase(new Phrase(noPesanan, fontNoPesanan));
//            cell.setBorder(0);
//            table.addCell(cell);


//            document.add(table);
//            Paragraph p5 = new Paragraph();
//            p5.setAlignment(Element.ALIGN_CENTER);
//            document.add(p5);

//            //total harga all item
//            cell = new PdfPCell();
//            cell.setPhrase(new Phrase("Total Harga : "));
//            cell.setBorder(0);
//            table.addCell(cell);
//
//            String totalHarga = decimalFormat.format(totalHargaItem);
//            Font fontTotalHarga = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
//            Paragraph pTotalHarga = new Paragraph(totalHarga);
//            pTotalHarga.setLeading(0, 1);
//            cell.setPhrase(new Phrase("Rp. " + totalHarga, fontTotalHarga));
//            cell.setBorder(0);
//            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            table.addCell(cell);

//            document.add(table);
//            Paragraph p5 = new Paragraph();
//            p5.setAlignment(Element.ALIGN_CENTER);
//            document.add(p5);


            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }


    }

}
