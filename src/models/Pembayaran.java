package models;

import java.sql.Date;

public class Pembayaran {
    private int idPembayaran;
    private int idTagihan;
    private Date tanggalBayar;
    private String metode;
    private double jumlahBayar;

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

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public int getIdTagihan() {
        return idTagihan;
    }

    public void setIdTagihan(int idTagihan) {
        this.idTagihan = idTagihan;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Date tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public void cetakStruk() {
        System.out.println("===== STRUK PEMBAYARAN =====");
        System.out.println("ID Pembayaran : " + idPembayaran);
        System.out.println("ID Tagihan    : " + idTagihan);
        System.out.println("Tanggal       : " + tanggalBayar);
        System.out.println("Metode        : " + metode);
        System.out.println("Jumlah        : Rp" + jumlahBayar);
        System.out.println("============================");
    }

    @Override
    public String toString() {
        return "Pembayaran{" +
                "idPembayaran=" + idPembayaran +
                ", idTagihan=" + idTagihan +
                ", tanggalBayar=" + tanggalBayar +
                ", metode='" + metode + '\'' +
                ", jumlahBayar=" + jumlahBayar +
                '}';
    }
}