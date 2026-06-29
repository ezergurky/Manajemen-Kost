package models;

import java.sql.Date;

public class Pembayaran {
    private int idPembayaran;
    private int idTagihan;
    private Date tanggalBayar;
    private String metode;
    private double jumlahBayar;

    private Tagihan tagihan;

    public Pembayaran(int idPembayaran, int idTagihan, Date tanggalBayar, String metode, double jumlahBayar) {
        this.idPembayaran = idPembayaran;
        this.idTagihan = idTagihan;
        this.tanggalBayar = tanggalBayar;
        this.metode = metode;
        this.jumlahBayar = jumlahBayar;
    }

    public int getIdPembayaran() { 
        return idPembayaran; 
    }

    public int getIdTagihan() { 
        return idTagihan; 
    }
    
    public Date getTanggalBayar() { 
        return tanggalBayar; 
    }

    public String getMetode() { 
        return metode; 
    }

    public double getJumlahBayar() {
        return jumlahBayar; 
    }

    public Tagihan getTagihan() { 
        return tagihan; 
    }

    public void setTagihan(Tagihan tagihan) {
        this.tagihan = tagihan;
    }
}