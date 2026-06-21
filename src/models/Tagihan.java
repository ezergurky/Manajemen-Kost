package models;

import java.sql.Date;

public class Tagihan {
    private int idTagihan;
    private int idPenghuni;
    private String bulan;
    private int tahun;
    private Date jatuhTempo;
    private double jumlah;
    private double denda;
    private String status;

    public Tagihan(int idTagihan, int idPenghuni, String bulan, int tahun, Date jatuhTempo, double jumlah, double denda, String status) {
        this.idTagihan = idTagihan;
        this.idPenghuni = idPenghuni;
        this.bulan = bulan;
        this.tahun = tahun;
        this.jatuhTempo = jatuhTempo;
        this.jumlah = jumlah;
        this.denda = denda;
        this.status = status;
    }

    public int getIdTagihan() {
        return idTagihan;
    }

    public void setIdTagihan(int idTagihan) {
        this.idTagihan = idTagihan;
    }

    public int getIdPenghuni() {
        return idPenghuni;
    }

    public void setIdPenghuni(int idPenghuni) {
        this.idPenghuni = idPenghuni;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public Date getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(Date jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double hitungTotal() {
        return this.jumlah + this.denda;
    }
}