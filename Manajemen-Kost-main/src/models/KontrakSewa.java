package models;

import java.util.Date;

public class KontrakSewa {
    private int idKontrak;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private String status;

    public KontrakSewa(int idKontrak, Date tanggalMulai, Date tanggalSelesai, String status) {
        this.idKontrak = idKontrak;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = status;
    }

    public void aktifkan() {
        this.status = "Aktif";
        System.out.println("Kontrak sewa denganID " + idKontrak + "Telah di aktifkan");
    }

    public void selesai() {
        this.status = "Selesai";
        System.out.println("Kontrak sewa denganID " + idKontrak + "Telah selesai");
    }

    public int getIdKontrak() {
        return idKontrak;
    }

    public void setIdKontrak(int idKontrak) {
        this.idKontrak = idKontrak;
    }

    public Date getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(Date tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public Date getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(Date tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "KontrakSewa{" +
                "idKontrak=" + idKontrak +
                ", tanggalMulai=" + tanggalMulai +
                ", tanggalSelesai=" + tanggalSelesai +
                ", status='" + status + '\'' +
                '}';
    }
}
