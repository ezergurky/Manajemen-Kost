package models;

import java.util.Date;

public class Pembayaran {
    private int idPembayaran;
    private Date tanggal;
    private double jumlah;
    private String status;

    public Pembayaran(int idPembayaran, Date tanggal, double jumlah, String status) {
        this.idPembayaran = idPembayaran;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.status = status;
    }

    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void konfirmasi() {
        status = "Lunas";
        System.out.println("Pembayaran berhasil dikonfirmasi.");
    }

    public void cetakStruk() {
        System.out.println("===== STRUK PEMBAYARAN =====");
        System.out.println("ID Pembayaran : " + idPembayaran);
        System.out.println("Tanggal       : " + tanggal);
        System.out.println("Jumlah        : Rp" + jumlah);
        System.out.println("Status        : " + status);
        System.out.println("============================");
    }

    @Override
    public String toString() {
        return "Pembayaran{" +
                "idPembayaran=" + idPembayaran +
                ", tanggal=" + tanggal +
                ", jumlah=" + jumlah +
                ", status='" + status + '\'' +
                '}';
    }
}